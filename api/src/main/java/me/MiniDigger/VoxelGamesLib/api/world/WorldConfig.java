package me.MiniDigger.VoxelGamesLib.api.world;

import java.util.ArrayList;
import java.util.List;

import me.MiniDigger.VoxelGamesLib.api.config.Config;

/**
 * Created by Martin on 07.10.2016.
 */
public class WorldConfig extends Config {

    public final int configVersion = 1;
    public int currentVersion = configVersion;
    public final List<String> maps = new ArrayList<>();
    // TODO we need to save the gamemodes for the maps here so that voting and stuff can easily pick maps

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
