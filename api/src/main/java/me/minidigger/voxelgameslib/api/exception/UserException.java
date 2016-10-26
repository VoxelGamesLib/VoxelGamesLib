package me.minidigger.voxelgameslib.api.exception;

/**
 * Thrown when something goes wrong related to users
 */
public class UserException extends VoxelGameLibException {
   
    /**
     * @param message the message that explains the issue
     */
    public UserException(String message) {
        super(message);
    }
}
