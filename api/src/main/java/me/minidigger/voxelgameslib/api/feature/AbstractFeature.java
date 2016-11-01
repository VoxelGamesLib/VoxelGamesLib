package me.minidigger.voxelgameslib.api.feature;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.phase.Phase;

/**
 * Abstract implementation of Phase
 */
public abstract class AbstractFeature implements Feature {
    
    private Phase phase;
    
    @Nonnull
    @Override
    public Phase getPhase() {
        return phase;
    }
    
    @Override
    public void setPhase(@Nonnull Phase phase) {
        this.phase = phase;
    }
}
