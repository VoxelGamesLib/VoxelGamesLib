package me.MiniDigger.VoxelGamesLib.api.game;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.phase.Phase;
import me.MiniDigger.VoxelGamesLib.api.user.User;

/**
 * Abstract implementation of a {@link Game}. Handles broadcasting, ticking and user management.
 */
public class AbstractGame implements Game {

    private GameMode gameMode;
    private Phase activePhase;

    /**
     * A list with all {@link User}s that participate in this game, aka should receive messages.
     */
    private List<User> users = new ArrayList<>();

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
    public void broadcastMessage(@Nonnull ChatMessage message) {
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
