package me.MiniDigger.VoxelGamesLib.api.config;

import javax.inject.Singleton;

/**
 * The config defines all configuration values (and the default values)
 */
@Singleton
public class Config {

    public static final int CONFIG_VERSION = 1;

    public int configVersion = CONFIG_VERSION;
    public boolean useRoleSystem = true;

    /**
     * @return the default config, with all default settings
     */
    public static Config getDefault() {
        return new Config();
    }
}
