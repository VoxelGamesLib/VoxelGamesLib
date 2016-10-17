package me.MiniDigger.VoxelGamesLib.api.exception;

import java.io.IOException;

/**
 * Thrown when something is wrong related to maps.
 */
public class MapException extends VoxelGameLibException {
    
    public MapException(String message) {
        super(message);
    }
    
    public MapException(String message, IOException ex) {
        super(message, ex);
    }
}
