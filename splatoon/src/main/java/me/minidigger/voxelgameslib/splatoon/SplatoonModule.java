package me.minidigger.voxelgameslib.splatoon;

import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.game.GameHandler;
import me.minidigger.voxelgameslib.api.game.GameMode;
import me.minidigger.voxelgameslib.api.module.Module;
import me.minidigger.voxelgameslib.api.module.ModuleInfo;

import jskills.GameRatingInfo;

/**
 * Created by Martin on 05.02.2017.
 */
@Singleton
@ModuleInfo(name = "Splatoon", authors = "MiniDigger", version = "1.0.0")
public class SplatoonModule implements Module {

    public static final GameMode GAMEMODE = new GameMode("Splatoon", SplatoonGame.class, GameRatingInfo.getDefaultGameInfo());

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
