package me.minidigger.voxelgameslib.api.exception;

/**
 * A global exception.
 */
public class VoxelGameLibException extends RuntimeException {
    
    VoxelGameLibException(String message) {
        super(message);
    }
    
    public VoxelGameLibException(String message, Exception e) {
        super(message, e);
    }
}
