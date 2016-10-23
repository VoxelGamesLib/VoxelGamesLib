package me.minidigger.voxelgameslib.api.exception;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.feature.Feature;

/**
 * Thrown when something tries to access a {@link Feature} in a {@link
 * me.minidigger.voxelgameslib.api.phase.Phase} that is not present at this time.
 */
public class NoSuchFeatureException extends VoxelGameLibException {
    
    /**
     * @param clazz the class of the feature that was tried to access
     */
    public NoSuchFeatureException(@Nonnull Class<Feature> clazz) {
        super("Could not find feature " + clazz.getSimpleName());
    }
    
}
