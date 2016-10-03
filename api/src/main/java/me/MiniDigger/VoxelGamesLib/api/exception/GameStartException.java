package me.MiniDigger.VoxelGamesLib.api.exception;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.game.Game;
import me.MiniDigger.VoxelGamesLib.api.game.GameMode;

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
