package me.minidigger.voxelgameslib.api.exception;

/**
 * A global exception, should not be thrown.
 */
public class VoxelGameLibException extends RuntimeException {
    
    /**
     * @param message the message that explains the issue
     */
    VoxelGameLibException(String message) {
        super(message);
    }
    
    /**
     * @param message the message that explains the issue
     * @param e       the root exception that caused this exception
     */
    VoxelGameLibException(String message, Exception e) {
        super(message, e);
    }
}
