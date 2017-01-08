package me.minidigger.voxelgameslib.api.game;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.game.GameLeaveEvent;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.tick.TickHandler;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * Abstract implementation of a {@link Game}. Handles broadcasting, ticking and user management.
 */
public abstract class AbstractGame implements Game {
    
    @Inject
    private Injector injector;
    @Inject
    private VGLEventHandler eventHandler;
    @Inject
    private TickHandler tickHandler;
    @Inject
    private Server server;
    
    @Nonnull
    private final GameMode gameMode;
    protected Phase activePhase;
    
    private UUID uuid;
    
    private int minPlayers;
    private int maxPlayers;
    
    private final List<User> players = new ArrayList<>();
    private final List<User> spectators = new ArrayList<>();
    
    private Map<String, Object> gameData = new HashMap<>();
    
    /**
     * Constructs a new {@link AbstractGame}
     *
     * @param mode the mode this {@link Game} is an instance of.
     */
    public AbstractGame(@Nonnull GameMode mode) {
        this.gameMode = mode;
    }
    
    @Override
    public void setUuid(@Nonnull UUID uuid) {
        this.uuid = uuid;
    }
    
    @Override
    public UUID getUuid() {
        return uuid;
    }
    
    @Override
    public void broadcastMessage(@Nonnull BaseComponent... message) {
        players.forEach(u -> u.sendMessage(message));
        spectators.forEach(u -> u.sendMessage(message));
        server.getConsoleUser().sendMessage(message);
    }
    
    @Override
    public void broadcastMessage(@Nonnull LangKey key, @Nullable Object... args) {
        players.forEach(user -> Lang.msg(user, key, args));
        spectators.forEach(user -> Lang.msg(user, key, args));
        Lang.msg(server.getConsoleUser(), key, args);
    }
    
    @Override
    public void start() {
        activePhase.setRunning(true);
        activePhase.start();
    }
    
    /**
     * @deprecated this method does nothing, use endGame instead ;)
     */
    @Deprecated
    @Override
    public void stop() {
        // ignore stop from tick handler, we only need to care about that stop if server shuts down
        // and then we know about it via the game handler and endGame() anyways
    }
    
    @Override
    public void initGameFromDefinition(@Nonnull GameDefinition gameDefinition) {
        setMaxPlayers(gameDefinition.getMaxPlayers());
        setMinPlayers(gameDefinition.getMinPlayers());
        activePhase = gameDefinition.getPhases().get(0);
        gameData = gameDefinition.getGameData();
        
        // fix stuff
        for (int i = 0; i < gameDefinition.getPhases().size(); i++) {
            Phase nextPhase = null;
            if (gameDefinition.getPhases().size() > i + 1) {
                nextPhase = gameDefinition.getPhases().get(i + 1);
            }
            Phase currPhase = gameDefinition.getPhases().get(i);
            currPhase.setNextPhase(nextPhase);
            currPhase.setGame(this);
    
            for (Feature feature : currPhase.getFeatures()) {
                feature.setPhase(currPhase);
            }
    
            for (Feature feature : currPhase.getFeatures()) {
                feature.init();
            }
        }
    }
    
    @Override
    @Nonnull
    public GameDefinition saveGameDefinition() {
        GameDefinition definition = new GameDefinition();
        definition.setGameMode(getGameMode());
        definition.setMaxPlayers(getMaxPlayers());
        definition.setMinPlayers(getMinPlayers());
        List<Phase> phases = new ArrayList<>();
        Phase phase = activePhase;
        while (phase != null) {
            phases.add(phase);
            phase = phase.getNextPhase();
        }
        definition.setPhases(phases);
        definition.setGameData(gameData);
        
        return definition;
    }
    
    @Override
    public void tick() {
        activePhase.tick();
    }
    
    @Override
    public void endPhase() {
        activePhase.setRunning(false);
        activePhase.stop();
        if (activePhase.getNextPhase() != null) {
            activePhase = activePhase.getNextPhase();
            assert activePhase != null;
            activePhase.setRunning(true);
            activePhase.start();
        } else {
            System.out.println("was last phase, so stop");
            new Throwable().printStackTrace();
            endGame();
        }
    }
    
    @Override
    public void endGame() {
        //TODO handle game end better, what about a game end phase?
        System.out.println("end game");
        activePhase.setRunning(false);
        activePhase.stop();
        tickHandler.end(this);
    }
    
    @Nonnull
    @Override
    public GameMode getGameMode() {
        return gameMode;
    }
    
    @Nonnull
    @Override
    public Phase getActivePhase() {
        return activePhase;
    }
    
    @Override
    public void join(@Nonnull User user) {
        if (!getActivePhase().allowJoin()) {
            spectate(user);
            return;
        }
        
        if (!isPlaying(user)) {
            players.add(user);
            eventHandler.callEvent(new GameJoinEvent(this, user));
            broadcastMessage(LangKey.GAME_PLAYER_JOIN, (Object) user.getDisplayName());
        }
    }
    
    @Override
    public void spectate(@Nonnull User user) {
        if (!getActivePhase().allowSpectate()) {
            Lang.msg(user, LangKey.GAME_CANT_SPECTATE);
        }
        
        if (!isPlaying(user) && !isSpectating(user)) {
            spectators.add(user);
        }
    }
    
    @Override
    public void leave(@Nonnull User user) {
        players.remove(user);
        spectators.remove(user);
        eventHandler.callEvent(new GameLeaveEvent(this, user));
        broadcastMessage(LangKey.GAME_PLAYER_LEAVE, (Object) user.getDisplayName());
    }
    
    @Override
    public boolean isPlaying(@Nonnull User user) {
        return players.contains(user);
    }
    
    @Override
    public boolean isSpectating(@Nonnull User user) {
        return spectators.contains(user);
    }
    
    @Override
    @Nonnull
    public <T extends Feature> T createFeature(@Nonnull Class<T> featureClass, @Nonnull Phase phase) {
        T feature = injector.getInstance(featureClass);
        feature.setPhase(phase);
        feature.init();
        return feature;
    }
    
    @Override
    @Nonnull
    public <T extends Phase> T createPhase(@Nonnull Class<T> phaseClass) {
        T phase = injector.getInstance(phaseClass);
        phase.setGame(this);
        phase.init();
        return phase;
    }
    
    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    @Override
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
    
    @Override
    public int getMinPlayers() {
        return minPlayers;
    }
    
    @Nonnull
    @Override
    public List<User> getPlayers() {
        return players;
    }
    
    @Nonnull
    @Override
    public List<User> getSpectators() {
        return spectators;
    }
    
    @Nullable
    @Override
    public Object getGameData(@Nonnull String key) {
        return gameData.get(key);
    }
    
    @Override
    public void putGameData(@Nonnull String key, @Nonnull Object data) {
        gameData.put(key, data);
    }
}
