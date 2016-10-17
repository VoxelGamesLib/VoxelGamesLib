package me.MiniDigger.VoxelGamesLib.api.exception;

/**
 * Thrown when something is wrong related to worlds
 */
public class WorldException extends VoxelGameLibException {
    
    
    public WorldException(String message) {
        super(message);
    }
    
    public WorldException(String message, Exception e) {
        super(message, e);
    }
}
