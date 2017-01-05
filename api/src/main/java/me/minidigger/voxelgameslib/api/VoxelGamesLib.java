package me.minidigger.voxelgameslib.api;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.config.ConfigHandler;
import me.minidigger.voxelgameslib.api.error.ErrorHandler;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.VoxelGameLibEnableEvent;
import me.minidigger.voxelgameslib.api.event.events.VoxelGamesLibDisableEvent;
import me.minidigger.voxelgameslib.api.fun.FunCommands;
import me.minidigger.voxelgameslib.api.game.GameHandler;
import me.minidigger.voxelgameslib.api.lang.LangHandler;
import me.minidigger.voxelgameslib.api.log.LoggerHandler;
import me.minidigger.voxelgameslib.api.map.MapHandler;
import me.minidigger.voxelgameslib.api.module.ModuleHandler;
import me.minidigger.voxelgameslib.api.role.RoleHandler;
import me.minidigger.voxelgameslib.api.tick.TickHandler;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.api.world.WorldHandler;

import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;

/**
 * The main class of this framework. Gets called by the main classes of the different server mods.
 */
@Singleton
public class VoxelGamesLib {
    
    @Inject
    private static TaskChainFactory taskChainFactory;
    
    @Inject
    private LoggerHandler loggerHandler;
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
    @Inject
    private VGLEventHandler eventHandler;
    
    @Nonnull
    private Injector injector;
    
    /**
     * Called when the server starts and/or the plugin gets loaded
     *
     * @param injector the injector that was used to create this class
     */
    public void onEnable(@Nonnull Injector injector) {
        this.injector = injector;
    
        loggerHandler.start();
    
        configHandler.start();
        langHandler.start();
        tickHandler.start();
        userHandler.start();
        roleHandler.start();
        errorHandler.start();
        mapHandler.start();
        worldHandler.start();
    
        gameHandler.start();
        moduleHandler.start();
    
        //load event and command stuff after modules so that modules get a chance to provide
        eventHandler.start();
        commandHandler.start();
    
        doAdditionalStuff();
    
        eventHandler.callEvent(new VoxelGameLibEnableEvent());
    }
    
    /**
     * Called when the server stops and/or the plugin gets disabled
     */
    public void onDisable() {
        eventHandler.callEvent(new VoxelGamesLibDisableEvent());
    
        loggerHandler.stop();
    
        configHandler.stop();
        langHandler.stop();
        tickHandler.stop();
        userHandler.stop();
        roleHandler.stop();
        errorHandler.stop();
        mapHandler.stop();
        worldHandler.stop();
    
        gameHandler.stop();
        moduleHandler.stop();
    
        eventHandler.stop();
        commandHandler.stop();
    
        injector = null;
    }
    
    /**
     * @return the injector that was used to create this class
     */
    @Nonnull
    public Injector getInjector() {
        return injector;
    }
    
    /**
     * Create a new (normal) chain using the right factory for this server mod
     *
     * @param <T> TODO what is this?
     * @return a normal task chain
     */
    @Nonnull
    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }
    
    /**
     * Create a new shared chain using the right factory for this server mod
     *
     * @param <T>  TODO what is this?
     * @param name the name of the new shared chain
     * @return a shared task chain
     */
    @Nonnull
    public static <T> TaskChain<T> newSharedChain(@Nonnull String name) {
        return taskChainFactory.newSharedChain(name);
    }
    
    private void doAdditionalStuff() {
        FunCommands funCommands = injector.getInstance(FunCommands.class);
        try {
            funCommands.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
