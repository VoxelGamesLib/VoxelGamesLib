package me.minidigger.voxelgameslib.api.game;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.tick.Tickable;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * A {@link Game} is the representation of an instance of a {@link GameMode}. Handles everything
 * that is related to that {@link GameMode}: starting, stopping, {@link Phase}s etc.
 */
public interface Game extends Tickable {
    
    /**
     * Sends a message to every {@link me.minidigger.voxelgameslib.api.user.User} that
     * is related to this {@link Game}. This could be a participant in the game or a spectator.
     */
    void broadcastMessage(@Nonnull BaseComponent... message);
    
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
    @Nonnull
    GameMode getGameMode();
    
    /**
     * @return returns the {@link Phase} that is currently active
     */
    @Nonnull
    Phase getActivePhase();
}
