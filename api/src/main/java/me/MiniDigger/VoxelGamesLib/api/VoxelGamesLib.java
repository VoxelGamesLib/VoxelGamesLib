package me.MiniDigger.VoxelGamesLib.api;

import com.google.inject.Singleton;

import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.command.CommandHandler;
import me.MiniDigger.VoxelGamesLib.api.config.ConfigHandler;
import me.MiniDigger.VoxelGamesLib.api.error.ErrorHandler;
import me.MiniDigger.VoxelGamesLib.api.game.GameHandler;
import me.MiniDigger.VoxelGamesLib.api.role.RoleHandler;
import me.MiniDigger.VoxelGamesLib.api.tick.TickHandler;
import me.MiniDigger.VoxelGamesLib.api.user.UserHandler;

/**
 * The main class of this framework. Gets called by the main classes of the different server mods.
 */
@Singleton
public class VoxelGamesLib {

    @Inject
    private ConfigHandler configHandler;
    @Inject
    private TickHandler tickHandler;
    @Inject
    private GameHandler gameHandler;
    @Inject
    private UserHandler userHandler;
    @Inject
    private RoleHandler roleHandler;
    @Inject
    private CommandHandler commandHandler;
    @Inject
    private ErrorHandler errorHandler;

    /**
     * Called when the server starts and/or the plugin gets loaded
     */
    public void onEnable() {
        configHandler.start();
        tickHandler.start();
        gameHandler.start();
        userHandler.start();
        roleHandler.start();
        commandHandler.start();
        errorHandler.start();
    }

    /**
     * Called when the server stops and/or the plugin gets disabled
     */
    public void onDisable() {
        configHandler.stop();
        tickHandler.stop();
        gameHandler.stop();
        userHandler.stop();
        roleHandler.stop();
        commandHandler.stop();
        errorHandler.stop();
    }
}
