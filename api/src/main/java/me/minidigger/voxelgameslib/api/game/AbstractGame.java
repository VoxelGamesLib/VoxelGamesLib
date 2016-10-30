package me.minidigger.voxelgameslib.api.game;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;

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
    
    private final GameMode gameMode;
    protected Phase activePhase;
    
    private int minPlayers;
    private int maxPlayers;
    
    private final List<User> players = new ArrayList<>();
    private final List<User> spectators = new ArrayList<>();
    
    /**
     * Constructs a new {@link AbstractGame}
     *
     * @param mode the mode this {@link Game} is an instance of.
     */
    public AbstractGame(@Nonnull GameMode mode) {
        this.gameMode = mode;
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
        activePhase.stop();
    }
    
    @Override
    public void endGame() {
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
    public void join(User user) {
        if (!isPlaying(user)) {
            players.add(user);
            broadcastMessage(LangKey.GAME_PLAYER_JOIN, (Object) user.getDisplayName());
        }
    }
    
    @Override
    public void spectate(User user) {
        if (!isPlaying(user) && !isSpectating(user)) {
            spectators.add(user);
        }
    }
    
    @Override
    public void leave(User user) {
        players.remove(user);
        spectators.remove(user);
        broadcastMessage(LangKey.GAME_PLAYER_LEAVE, (Object) user.getDisplayName());
    }
    
    @Override
    public boolean isPlaying(User user) {
        return players.contains(user);
    }
    
    @Override
    public boolean isSpectating(User user) {
        return spectators.contains(user);
    }
    
    @Override
    public <T extends Feature> T createFeature(Class<T> featureClass, Phase phase) {
        T feature = injector.getInstance(featureClass);
        feature.setPhase(phase);
        feature.init();
        return feature;
    }
    
    @Override
    public <T extends Phase> T createPhase(Class<T> phaseClass) {
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
}
