package me.minidigger.voxelgameslib.api.exception;

import java.io.IOException;

/**
 * Thrown when something is wrong related to maps.
 */
public class MapException extends VoxelGameLibException {
    
    /**
     * @param message the message that explains the issue
     */
    public MapException(String message) {
        super(message);
    }
    
    /**
     * Used for IO related exceptions
     *
     * @param message the message that explains the issue
     * @param ex      the root issue that was thrown
     */
    public MapException(String message, IOException ex) {
        super(message, ex);
    }
}
