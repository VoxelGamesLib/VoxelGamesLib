package me.minidigger.voxelgameslib.api.config;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.utils.CollectionUtil;

/**
 * The config defines all configuration values (and the default values)
 */
@Singleton
public class GlobalConfig extends Config {

    @Expose
    public final int configVersion = 5;
    @Expose
    public int currentVersion = configVersion;

    @Expose
    public String logLevel = Level.INFO.getName();
    @Expose
    public boolean useRoleSystem = true;
    @Expose
    public List<String> availableLanguages = CollectionUtil.toStringList(Locale.values(), Locale::getTag);

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
