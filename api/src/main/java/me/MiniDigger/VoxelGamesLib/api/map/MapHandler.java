package me.MiniDigger.VoxelGamesLib.api.map;

import java.util.HashMap;

import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.handler.Handler;

/**
 * Created by Martin on 04.10.2016.
 */
@Singleton
public class MapHandler implements Handler {

    private HashMap<String, ChestMarker> chests = new HashMap<>();

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
