package me.minidigger.voxelgameslib.api.exception;

/**
 * Thrown when something is wrong related to worlds
 */
public class WorldException extends VoxelGameLibException {
    
    /**
     * @param message the message that explains the issue
     */
    public WorldException(String message) {
        super(message);
    }
    
    /**
     * Used for IO related exceptions
     *
     * @param message the message that explains the issue
     * @param e       the root issue that was thrown
     */
    public WorldException(String message, Exception e) {
        super(message, e);
    }
}
