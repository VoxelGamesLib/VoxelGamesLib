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
    }
    
    @Override
    public void broadcastMessage(@Nonnull LangKey key, Object... args) {
        players.forEach(user -> Lang.msg(user, key, args));
        spectators.forEach(user -> Lang.msg(user, key, args));
    }
    
    @Override
    public void start() {
        activePhase.start();
    }
    
    @Override
    public void stop() {
        // ignore stop from tick handler, we only need to care about that stop if sever shuts down
        // and then we know about it via the game handler and endGame() anyways
    }
    
    @Override
    public void tick() {
        activePhase.tick();
    }
    
    @Override
    public void endPhase() {
        if (activePhase.getNextPhase() != null) {
            activePhase.stop();
            activePhase = activePhase.getNextPhase();
            assert activePhase != null;
            activePhase.start();
        } else {
            endGame();
        }
    }
    
    @Override
    public void endGame() {
        new Throwable().printStackTrace();
        System.out.println("end game");
        activePhase.stop();
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
        if (!isPlaying(user)) {
            players.add(user);
            eventHandler.callEvent(new GameJoinEvent(this, user));
            broadcastMessage(LangKey.GAME_PLAYER_JOIN, (Object) user.getDisplayName());
        }
    }
    
    @Override
    public void spectate(@Nonnull User user) {
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
    
    @Override
    public List<User> getPlayers() {
        return players;
    }
    
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
