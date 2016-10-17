package me.MiniDigger.VoxelGamesLib.api.exception;

/**
 * Thrown when _something_ (a command, most of the time) tries to access a role that does not exist.
 */
public class NoSuchRoleException extends VoxelGameLibException {
    
    /**
     * @param role the name of the role that was tried to access
     */
    public NoSuchRoleException(String role) {
        super("Could not find role " + role);
    }
}
