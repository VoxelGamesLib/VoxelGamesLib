package me.MiniDigger.VoxelGamesLib.api.config;

/**
 * Superclass for config classes
 */
public abstract class Config {
    
    
    public abstract int getConfigVersion();
    
    public abstract int getCurrentVersion();
    
    public abstract void setCurrentVersion(int currentVersion);
}
