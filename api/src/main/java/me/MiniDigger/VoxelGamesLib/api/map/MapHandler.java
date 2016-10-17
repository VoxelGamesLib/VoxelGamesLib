package me.MiniDigger.VoxelGamesLib.api.map;

import me.MiniDigger.VoxelGamesLib.api.handler.Handler;

import javax.inject.Singleton;
import java.util.HashMap;

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
