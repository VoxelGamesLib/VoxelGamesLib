package me.minidigger.voxelgameslib.api.world;

import java.util.ArrayList;
import java.util.List;

import me.minidigger.voxelgameslib.api.config.Config;

/**
 * The config that knows about all world/maps that are playable
 */
public class WorldConfig extends Config {
    
    public final int configVersion = 1;
    public int currentVersion = configVersion;
    public final List<String> maps = new ArrayList<>();
    // TODO we need to save the gamemodes for the maps here so that voting and stuff can easily pick maps
    
    /**
     * @return the default values for this config
     */
    public static WorldConfig getDefault() {
        return new WorldConfig();
    }
    
    @Override
    public int getConfigVersion() {
        return configVersion;
    }
    
    @Override
    public int getCurrentVersion() {
        return currentVersion;
    }
    
    @Override
    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }
}
