package me.minidigger.voxelgameslib.api.signs;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.VoxelGamesLib;
import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.persistence.PersistenceHandler;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.timings.Timings;

import lombok.extern.java.Log;

/**
 * Handles placeholder sign and interaction signs
 */
@Log
@Singleton
public class SignHandler implements Handler {

    @Inject
    private VGLEventHandler eventHandler;
    @Inject
    private PersistenceHandler persistenceHandler;
    @Inject
    private Server server;

    private Map<String, SignPlaceHolder> placeHolders;
    private List<SignLocation> signLocations;

    private boolean dirty = false;

    @Override
    public void start() {
        placeHolders = new HashMap<>();

        signLocations = persistenceHandler.getProvider().loadSigns();

        placeHolders.put("world", (SimpleSignPlaceHolder) (event, key) -> event.getWorld());
        placeHolders.put("time", (SimpleSignPlaceHolder) (event, key) -> DateTimeFormatter.ISO_TIME.format(LocalTime.now()));
        placeHolders.put("location", (SimpleSignPlaceHolder) (event, key) -> event.getLocation().toString().replace("V", ""));

        startUpdateTask();
    }

    @Override
    public void stop() {
        placeHolders.clear();
        placeHolders = null;
    }

    /**
     * gets map with all registered sign placeholders
     *
     * @return all sign placeholders
     */
    public Map<String, SignPlaceHolder> getPlaceHolders() {
        return placeHolders;
    }

    /**
     * Starts the task to update signs
     */
    public void startUpdateTask() {
        VoxelGamesLib.newChain().delay(1, TimeUnit.MINUTES).sync(this::updateSigns).execute(this::startUpdateTask, (e, t) -> {
            log.warning("Error while updating signs, trying again...");
            e.printStackTrace();
            startUpdateTask();
        });

        if (dirty) {
            persistenceHandler.getProvider().saveSigns(signLocations);
            dirty = false;
        }
    }

    private void updateSigns() {
        Timings.time("UpdateSigns", () -> {
            Iterator<SignLocation> iterator = signLocations.listIterator();
            while (iterator.hasNext()) {
                SignLocation loc = iterator.next();
                if (loc.isStillValid()) {
                    loc.fireUpdateEvent(eventHandler);
                } else {
                    log.finer("Removing old placeholder sign at " + loc.getWorld() + " " + loc.getLocation());
                    iterator.remove();
                }
            }
        });
    }

    /**
     * Removes a sign from the sign location list
     *
     * @param block the block to remove
     */
    public void removeSign(Block block) {
        dirty = true;
        ListIterator<SignLocation> iterator = signLocations.listIterator();
        while (iterator.hasNext()) {
            SignLocation loc = iterator.next();
            if (loc.getWorld().equals(block.getWorld()) && loc.getLocation().equals(block.getLocation())) {
                iterator.remove();
            }
        }
    }

    /**
     * Adds a entry to the sign location list
     *
     * @param location the location of the new sign
     * @param world    the world of the new sign
     */
    public void addSign(Vector3D location, String world) {
        dirty = true;
        signLocations.add(new SignLocation(location, world, server));
    }
}
