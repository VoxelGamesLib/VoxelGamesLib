package me.MiniDigger.VoxelGamesLib.api;

import me.MiniDigger.VoxelGamesLib.api.game.GameHandler;
import me.MiniDigger.VoxelGamesLib.api.tick.TickHandler;

/**
 * The mainclass of this framework. Gets called by the main classes of the different server mods.
 */
public class VoxelGamesLib {

    private TickHandler tickHandler;
    private GameHandler gameHandler;
}
