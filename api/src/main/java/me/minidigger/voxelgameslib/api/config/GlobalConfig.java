package me.minidigger.voxelgameslib.api.config;

import com.google.gson.annotations.Expose;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

/**
 * The config defines all configuration values (and the default values)
 */
@Singleton
public class GlobalConfig extends Config {
    
    @Expose
    public final int configVersion = 3;
    @Expose
    public int currentVersion = configVersion;
    
    @Expose
    public final boolean useRoleSystem = true;
    @Expose
    public int anInt = 10;
    @Expose
    public double aDouble = 10;
    @Expose
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
