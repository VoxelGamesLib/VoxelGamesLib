package me.MiniDigger.VoxelGamesLib.api.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.exception.ConfigException;
import me.MiniDigger.VoxelGamesLib.api.handler.Handler;

/**
 * the config handler handles all configs (uhh)
 */
@Log
@Singleton
public class ConfigHandler implements Handler, Provider<GlobalConfig> {

    @Inject
    @Named("ConfigFolder")
    private File configFolder;

    @Inject
    private Gson gson;

    private File globalConfigFile;
    private GlobalConfig globalConfig;

    @Override
    public void start() {
        globalConfigFile = new File(configFolder, "config.json");
        if (!globalConfigFile.exists()) {
            log.warning("Did not found global config, saving default");
            globalConfig = GlobalConfig.getDefault();
            saveConfig(globalConfigFile, globalConfig);
        } else {
            log.info("Loading global config");
            globalConfig = loadConfig(globalConfigFile, GlobalConfig.class);


            if (checkMigrate(globalConfig)) {
                migrate(globalConfigFile, globalConfig);
            }
        }
    }

    /**
     * Checks if the config needs to be migrated
     *
     * @param config the config that should be checked
     * @return if the config needs to be migrated
     */
    public boolean checkMigrate(Config config) {
        return config.getConfigVersion() != config.getCurrentVersion();
    }

    /**
     * Migrates the config to a new config version.
     *
     * @param configFile the file to migrate
     * @param config     the config to migrate
     * @throws ConfigException if there was an error while creating an backup
     */
    public void migrate(File configFile, Config config) {
        log.info("Migrating config from v" + config.getCurrentVersion() + " to v" + config.getConfigVersion());
        try {
            File backup = new File(configFile.getParent(), configFile.getName() + ".v" + config.getCurrentVersion() + ".backup");
            Files.copy(configFile, backup);
            log.info("Saved backup to " + backup.getAbsolutePath());
        } catch (IOException e) {
            throw new ConfigException("Error while migrating config", e);
        }

        config.setCurrentVersion(config.getConfigVersion());
        saveConfig(configFile, config);
        log.info("Done migrating");
    }

    @Override
    public void stop() {

    }

    /**
     * (Re)Loads the config
     *
     * @param clazz      the class of the config
     * @param configFile the file to load
     * @return the loaded config
     * @throws ConfigException if something went wrong
     */
    public <T extends Config> T loadConfig(File configFile, Class<T> clazz) {
        log.finer("Loading " + clazz.getName() + " from " + configFile.getAbsolutePath());
        try {
            return gson.fromJson(new JsonReader(new FileReader(configFile)), clazz);
        } catch (Exception e) {
            throw new ConfigException("Error while loading config", e);
        }
    }

    /**
     * saves the config
     *
     * @param configFile the file that should be saved to
     * @param config     the config that should be saved
     * @throws ConfigException if something went wrong
     */
    public void saveConfig(File configFile, Config config) {
        log.finer("Saving " + config.getClass().getName() + " to " + configFile.getAbsolutePath());
        if (!configFile.exists()) {
            try {
                if (configFile.getParentFile() != null) {
                    configFile.getParentFile().mkdirs();
                }
                configFile.createNewFile();
            } catch (Exception e) {
                throw new ConfigException("Error while creating config file. Does that server has rw-rights to '" + configFile.getAbsolutePath() + "'?", e);
            }
        }

        if (config == null) {
            log.warning("Tried to save a null config!");
            return;
        }

        try {
            Writer writer = new FileWriter(configFile, false);
            gson.toJson(config, writer);
            writer.close();
        } catch (Exception e) {
            throw new ConfigException("Error while saving config", e);
        }
    }

    @Override
    public GlobalConfig get() {
        return globalConfig;
    }
}
