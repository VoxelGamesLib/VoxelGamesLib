package me.minidigger.voxelgameslib.api;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.config.ConfigHandler;
import me.minidigger.voxelgameslib.api.error.ErrorHandler;
import me.minidigger.voxelgameslib.api.game.GameCommands;
import me.minidigger.voxelgameslib.api.game.GameHandler;
import me.minidigger.voxelgameslib.api.lang.LangCommands;
import me.minidigger.voxelgameslib.api.lang.LangHandler;
import me.minidigger.voxelgameslib.api.map.MapHandler;
import me.minidigger.voxelgameslib.api.module.ModuleHandler;
import me.minidigger.voxelgameslib.api.role.RoleHandler;
import me.minidigger.voxelgameslib.api.tick.TickHandler;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.api.world.EditMode;
import me.minidigger.voxelgameslib.api.world.WorldCommands;
import me.minidigger.voxelgameslib.api.world.WorldCreator;
import me.minidigger.voxelgameslib.api.world.WorldHandler;

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
    @Inject
    private MapHandler mapHandler;
    @Inject
    private WorldHandler worldHandler;
    @Inject
    private LangHandler langHandler;
    @Inject
    private ModuleHandler moduleHandler;
    
    private Injector injector;
    
    /**
     * Called when the server starts and/or the plugin gets loaded
     *
     * @param injector the injector that was used to create this class
     */
    public void onEnable(Injector injector) {
        this.injector = injector;
        
        configHandler.start();
        langHandler.start();
        tickHandler.start();
        gameHandler.start();
        userHandler.start();
        roleHandler.start();
        commandHandler.start();
        errorHandler.start();
        mapHandler.start();
        worldHandler.start();
        moduleHandler.start();
        
        commandHandler.register(injector.getInstance(WorldCommands.class));
        commandHandler.register(injector.getInstance(LangCommands.class));
        commandHandler.register(injector.getInstance(WorldCreator.class));
        commandHandler.register(injector.getInstance(EditMode.class));
        commandHandler.register(injector.getInstance(GameCommands.class));
    }
    
    /**
     * Called when the server stops and/or the plugin gets disabled
     */
    public void onDisable() {
        configHandler.stop();
        langHandler.stop();
        tickHandler.stop();
        gameHandler.stop();
        userHandler.stop();
        roleHandler.stop();
        commandHandler.stop();
        errorHandler.stop();
        mapHandler.stop();
        worldHandler.stop();
        moduleHandler.stop();
        
        injector = null;
    }
    
    /**
     * @return the injector that was used to create this class
     */
    public Injector getInjector() {
        return injector;
    }
}
