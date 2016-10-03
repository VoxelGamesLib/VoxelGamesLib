package me.MiniDigger.VoxelGamesLib.api.role;

/**
 * Created by Martin on 03.10.2016.
 */
public class Permission {

    private String string;
    private Role role;

    Permission(String string, Role role) {
        this.string = string;
        this.role = role;
    }

    public String getString() {
        return string;
    }

    public Role getRole() {
        return role;
    }
}
