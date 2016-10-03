package me.MiniDigger.VoxelGamesLib.api.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.annotation.Nullable;
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
public class ConfigHandler implements Handler, Provider<Config> {

    @Inject
    @Named("ConfigFile")
    private File configFile;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Config config;

    @Override
    public void start() {
        if (!configFile.exists()) {
            log.warning("Did not found config, saving default");
            config = Config.getDefault();
            saveConfig();
        } else {
            log.info("Loading config");
            loadConfig();

            if (config.configVersion != Config.CONFIG_VERSION) {
                migrate();
            }
        }
    }

    /**
     * Migrates the config to a new config version.
     *
     * @throws ConfigException if there was an error while creating an backup
     */
    private void migrate() {
        log.info("Migrating config from v" + config.configVersion + " to v" + Config.CONFIG_VERSION);
        try {
            File backup = new File(configFile.getParent(), configFile.getName() + ".v" + config.configVersion + ".backup");
            Files.copy(configFile, backup);
            log.info("Saved backup to " + backup.getAbsolutePath());
        } catch (IOException e) {
            throw new ConfigException("Error while migrating config", e);
        }

        config.configVersion = Config.CONFIG_VERSION;
        saveConfig();
        log.info("Done migrating");
    }

    @Override
    public void stop() {

    }

    /**
     * (Re)Loads the config
     *
     * @throws ConfigException if something went wrong
     */
    public void loadConfig() {
        try {
            config = gson.fromJson(new JsonReader(new FileReader(configFile)), Config.class);
        } catch (Exception e) {
            throw new ConfigException("Error while loading config", e);
        }

        //TODO need to figure out what happens if config version changes
    }

    /**
     * saves the config
     *
     * @throws ConfigException if something went wrong
     */
    public void saveConfig() {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs();
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
    @Nullable
    public Config get() {
        return config;
    }
}
