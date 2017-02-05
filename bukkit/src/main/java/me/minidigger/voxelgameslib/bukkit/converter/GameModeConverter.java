package me.minidigger.voxelgameslib.bukkit.converter;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.convert.Converter;
import me.minidigger.voxelgameslib.api.user.GameMode;

/**
 * Created by Martin on 14.01.2017.
 */
@Singleton
public class GameModeConverter implements Converter<GameMode, org.bukkit.GameMode> {
    @Override
    public org.bukkit.GameMode fromVGL(GameMode gameMode) {
        return org.bukkit.GameMode.valueOf(gameMode.name());
    }

    @Override
    public GameMode toVGL(org.bukkit.GameMode gameMode) {
        return GameMode.valueOf(gameMode.name());
    }
}
