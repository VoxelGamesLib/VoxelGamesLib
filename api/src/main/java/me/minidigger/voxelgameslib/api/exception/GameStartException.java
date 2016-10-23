package me.minidigger.voxelgameslib.api.exception;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.game.Game;
import me.minidigger.voxelgameslib.api.game.GameMode;

/**
 * Thrown when something goes wrong while starting a {@link Game}
 */
public class GameStartException extends VoxelGameLibException {
    
    /**
     * @param mode the gamemode that was tried to start
     * @param e    the exception that was thrown while starting
     */
    public GameStartException(@Nonnull GameMode mode, @Nonnull Exception e) {
        super("Error while starting the game " + mode.getName(), e);
    }
}
