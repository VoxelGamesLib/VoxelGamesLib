package me.MiniDigger.VoxelGamesLib.api.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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

    private Gson gson = new Gson();
    private Config config;

    @Override
    public void start() {
        if (!configFile.exists()) {
            log.info("Did not found config, saving default");
            config = Config.getDefault();
            saveConfig();
        } else {
            loadConfig();
        }
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

        try {
            gson.toJson(config, Config.class, new JsonWriter(new FileWriter(configFile)));
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
