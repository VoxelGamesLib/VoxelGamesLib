package me.MiniDigger.VoxelGamesLib.api;

import me.MiniDigger.VoxelGamesLib.api.game.GameHandler;
import me.MiniDigger.VoxelGamesLib.api.tick.TickHandler;

/**
 * The main class of this framework. Gets called by the main classes of the different server mods.
 */
public class VoxelGamesLib {

    private TickHandler tickHandler;
    private GameHandler gameHandler;


    /**
     * Called when the server starts and/or the plugin gets loaded
     */
    public void onEnable() {

    }

    /**
     * Called when the server stops and/or the plugin gets disabled
     */
    public void onDisable() {

    }
}
