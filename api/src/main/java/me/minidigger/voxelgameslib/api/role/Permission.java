package me.minidigger.voxelgameslib.api.role;

/**
 * A permission is the ability to do something. a user can get a permission to do something via his
 * role or manually via the string.
 */
public class Permission {
    
    private final String string;
    private final Role role;
    
    Permission(String string, Role role) {
        this.string = string;
        this.role = role;
    }
    
    /**
     * @return the string representation of this permission
     */
    public String getString() {
        return string;
    }
    
    /**
     * @return the role that has this permission by default
     */
    public Role getRole() {
        return role;
    }
}
