package me.MiniDigger.VoxelGamesLib.api.exception;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.exception.VoxelGameLibException;
import me.MiniDigger.VoxelGamesLib.api.feature.Feature;

/**
 * Thrown when something tries to access a {@link Feature} in a {@link me.MiniDigger.VoxelGamesLib.api.phase.Phase} that is not present at
 * this time.
 */
public class NoSuchFeatureException extends VoxelGameLibException {

    private static final long serialVersionUID = 2711461799912246262L;

    public NoSuchFeatureException(@Nonnull Class<Feature> clazz) {
        // TODO Auto-generated constructor stub
    }

}
