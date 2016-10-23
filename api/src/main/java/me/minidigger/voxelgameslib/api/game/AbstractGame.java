package me.minidigger.voxelgameslib.api.game;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * Abstract implementation of a {@link Game}. Handles broadcasting, ticking and user management.
 */
public class AbstractGame implements Game {
    
    private final GameMode gameMode;
    private final Phase activePhase;
    
    /**
     * A list with all {@link User}s that participate in this game, aka should receive messages.
     */
    private final List<User> users = new ArrayList<>();
    
    /**
     * Constructs a new {@link AbstractGame}
     *
     * @param mode       the mode this {@link Game} is an instance of.
     * @param firstPhase the first {@link Phase}
     */
    public AbstractGame(@Nonnull GameMode mode, @Nonnull Phase firstPhase) {
        this.gameMode = mode;
        this.activePhase = firstPhase;
    }
    
    @Override
    public void broadcastMessage(@Nonnull BaseComponent... message) {
        users.forEach(u -> u.sendMessage(message));
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
}
