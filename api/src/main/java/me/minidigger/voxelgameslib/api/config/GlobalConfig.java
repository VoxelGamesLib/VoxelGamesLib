package me.minidigger.voxelgameslib.api.config;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

/**
 * The config defines all configuration values (and the default values)
 */
@Singleton
public class GlobalConfig extends Config {
    
    public final int configVersion = 3;
    public int currentVersion = configVersion;
    
    public final boolean useRoleSystem = true;
    public int anInt = 10;
    public double aDouble = 10;
    public double anotherDouble = 10.1020;
    
    /**
     * @return the default config, with all default settings
     */
    @Nonnull
    public static GlobalConfig getDefault() {
        return new GlobalConfig();
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
