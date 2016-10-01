package me.MiniDigger.VoxelGamesLib.api.game;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.error.VoxelGameLibException;

/**
 * Thrown when trying to do something with a {@link GameMode} that is not registered on this server
 */
public class GameModeNotAvailableException extends VoxelGameLibException {

    private static final long serialVersionUID = 7018826370298874901L;

    public GameModeNotAvailableException(@Nonnull GameMode mode) {
        // TODO Auto-generated constructor stub
    }

}
