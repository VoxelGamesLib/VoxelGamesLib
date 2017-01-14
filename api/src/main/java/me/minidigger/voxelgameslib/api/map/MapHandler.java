package me.minidigger.voxelgameslib.api.map;

import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.handler.Handler;

/**
 * Created by Martin on 04.10.2016.
 */
@Singleton
public class MapHandler implements Handler {

    @Nonnull
    private HashMap<String, ChestMarker> chests = new HashMap<>();

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
