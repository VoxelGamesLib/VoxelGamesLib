package me.MiniDigger.VoxelGamesLib.api.game;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.phase.Phase;
import me.MiniDigger.VoxelGamesLib.api.tick.Tickable;

/**
 * A {@link Game} is the representation of an instance of a {@link GameMode}. Handles everything
 * that is related to that {@link GameMode}: starting, stopping, {@link Phases} etc.
 *
 */
public interface Game extends Tickable {

    /**
     * Sends a {@link ChatMessage} to every {@link User} that is related to this {@link Game}. This
     * could be a participant in the game or a spectator.
     */
    void broadcastMessage(@Nonnull ChatMessage message);

    /**
     * Ends the current {@link Phase} and starts the next one.
     */
    void endPhase();

    /**
     * Ends the {@link Game}, handles who has won.
     */
    void endGame();

    /**
     * @return the {@link GameMode} this {@link Game} is an instance of.
     */
    GameMode getGameMode();

    /**
     * @return returns the {@link Phase} that is currently active
     */
    Phase getActivePhase();
}
