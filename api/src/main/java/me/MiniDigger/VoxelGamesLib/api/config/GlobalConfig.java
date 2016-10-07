package me.MiniDigger.VoxelGamesLib.api.config;

import javax.inject.Singleton;

/**
 * The config defines all configuration values (and the default values)
 */
@Singleton
public class GlobalConfig extends Config {

    public boolean useRoleSystem = true;

    /**
     * @return the default config, with all default settings
     */
    public static GlobalConfig getDefault() {
        return new GlobalConfig();
    }
}
