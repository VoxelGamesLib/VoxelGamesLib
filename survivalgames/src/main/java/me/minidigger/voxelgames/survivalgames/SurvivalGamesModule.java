package me.minidigger.voxelgames.survivalgames;

import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.game.GameHandler;
import me.minidigger.voxelgameslib.api.game.GameMode;
import me.minidigger.voxelgameslib.api.module.Module;
import me.minidigger.voxelgameslib.api.module.ModuleInfo;

/**
 * Created by Martin on 26.10.2016.
 */
@Singleton
@ModuleInfo(name = "SurvivalGames", authors = "MiniDigger", version = "1.0.0")
public class SurvivalGamesModule implements Module {
    
    public static final GameMode GAMEMODE = new GameMode("SurvivalGames", SurvivalGamesGame.class);
    
    @Inject
    private GameHandler gameHandler;
    
    public void enable() {
        gameHandler.registerGameMode(GAMEMODE);
    }
    
    public void disable() {
        
    }
    
    @Nonnull
    @Override
    public ModuleInfo getModuleInfo() {
        return getClass().getAnnotation(ModuleInfo.class); //TODO better module info handling
    }
}
