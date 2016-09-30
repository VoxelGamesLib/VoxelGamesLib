package me.MiniDigger.VoxelGamesLib.api.feature;

import me.MiniDigger.VoxelGamesLib.api.error.VoxelGameLibException;

/**
 * Thrown when something tries to access a {@link Feature} in a {@link Phase} that is not present at
 * this time.
 */
public class NoSuchFeatureException extends VoxelGameLibException {

    private static final long serialVersionUID = 2711461799912246262L;

    public NoSuchFeatureException(Class<Feature> clazz) {
        // TODO Auto-generated constructor stub
    }

}
