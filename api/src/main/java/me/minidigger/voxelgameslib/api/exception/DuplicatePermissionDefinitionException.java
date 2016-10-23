package me.minidigger.voxelgameslib.api.exception;

import me.minidigger.voxelgameslib.api.role.Permission;

/**
 * Thrown when something tries to register a new permission for a permission that already exist with
 * a different role
 */
public class DuplicatePermissionDefinitionException extends VoxelGameLibException {
    /**
     * @param permission the already existing permission
     * @param role       the new role
     */
    public DuplicatePermissionDefinitionException(Permission permission, String role) {
        super("Tried to register overlapping permission " + permission.getString() + ": ord role: " + permission.getRole().getName() + ", new role: " + role);
    }
}
