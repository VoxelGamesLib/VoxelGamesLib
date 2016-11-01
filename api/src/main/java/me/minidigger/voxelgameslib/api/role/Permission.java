package me.minidigger.voxelgameslib.api.role;

import javax.annotation.Nonnull;

/**
 * A permission is the ability to do something. a user can get a permission to do something via his
 * role or manually via the string.
 */
public class Permission {
    
    @Nonnull
    private final String string;
    @Nonnull
    private final Role role;
    
    Permission(@Nonnull String string, @Nonnull Role role) {
        this.string = string;
        this.role = role;
    }
    
    /**
     * @return the string representation of this permission
     */
    @Nonnull
    public String getString() {
        return string;
    }
    
    /**
     * @return the role that has this permission by default
     */
    @Nonnull
    public Role getRole() {
        return role;
    }
}
