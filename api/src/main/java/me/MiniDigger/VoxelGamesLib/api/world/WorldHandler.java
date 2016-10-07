package me.MiniDigger.VoxelGamesLib.api.world;

import com.google.inject.Provider;
import com.google.inject.name.Named;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.config.ConfigHandler;
import me.MiniDigger.VoxelGamesLib.api.exception.WorldException;
import me.MiniDigger.VoxelGamesLib.api.handler.Handler;
import me.MiniDigger.VoxelGamesLib.api.map.Map;
import me.MiniDigger.VoxelGamesLib.api.utils.FileUtils;

/**
 * Handles the worlds (loading, unloading etc)
 */
@Log
@Singleton
public class WorldHandler implements Handler, Provider<WorldConfig> {

    @Inject
    @Named("WorldsFolder")
    private File worldsFolder;

    @Inject
    @Named("WorldContainer")
    private File worldContainer;

    @Inject
    private ConfigHandler configHandler;

    private WorldConfig config;
    private File configFile = new File(worldsFolder, "worlds.json");

    /**
     * Loads a world. Needs to copy the file from the repo, unzip it and let the implementation load
     * it <br><b>Always needs to call super! Super needs to be called first (because it copies the
     * world)</b>
     *
     * @param map the map that should be loaded
     * @throws WorldException something goes wrong
     */
    public void loadWorld(Map map) {
        map.setLoaded(true);

        try {
            ZipFile zip = new ZipFile(new File(worldsFolder, map.getWorldName()));
            zip.extractAll(new File(worldContainer, map.getWorldName()).getAbsolutePath());
        } catch (ZipException e) {
            throw new WorldException("Could not unzip world " + map.getWorldName() + ".", e);
        }
    }

    /**
     * Unloads a world. Needs to lets the implementation unload the world and delete the folder
     * <br><b>Always needs to call super! Super needs to be called last (because it deletes the
     * world folder)</b>
     *
     * @param map the map that should be unloaded.
     */
    public void unloadWorld(Map map) {
        map.setLoaded(false);

        FileUtils.delete(new File(worldContainer, map.getWorldName()));
    }

    @Override
    public void start() {
        if (!worldsFolder.exists()) {
            log.warning("Could not find worldsfolder " + worldsFolder.getAbsolutePath() + ". Creating...");
            worldsFolder.mkdirs();
        }

        if (!configFile.exists()) {
            log.warning("Did not found world config, saving default");
            config = WorldConfig.getDefault();
            configHandler.saveConfig(configFile, config);
        } else {
            log.info("Loading world config");
            config = configHandler.loadConfig(configFile, WorldConfig.class);


            if (configHandler.checkMigrate(config)) {
                configHandler.migrate(configFile, config);
            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public WorldConfig get() {
        return config;
    }
}
