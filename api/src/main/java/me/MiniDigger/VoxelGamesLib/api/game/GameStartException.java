package me.MiniDigger.VoxelGamesLib.api.game;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.error.VoxelGameLibException;

/**
 * Thrown when something goes wrong while starting a {@link Game}
 */
public class GameStartException extends VoxelGameLibException {

    private static final long serialVersionUID = 6204377462121639450L;

    public GameStartException(@Nonnull GameMode mode, @Nonnull ReflectiveOperationException e) {
        // TODO Auto-generated constructor stub
    }
}
