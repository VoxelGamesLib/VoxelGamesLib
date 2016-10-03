package me.MiniDigger.VoxelGamesLib.api.exception;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.feature.Feature;

/**
 * Thrown when something tries to access a {@link Feature} in a {@link
 * me.MiniDigger.VoxelGamesLib.api.phase.Phase} that is not present at this time.
 */
public class NoSuchFeatureException extends VoxelGameLibException {

    /**
     * @param clazz the class of the feature that was tried to access
     */
    public NoSuchFeatureException(@Nonnull Class<Feature> clazz) {
        super("Could not find feature " + clazz.getSimpleName());
    }

}
