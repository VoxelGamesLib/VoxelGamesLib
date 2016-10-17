package me.MiniDigger.VoxelGamesLib.api.user;

import me.MiniDigger.VoxelGamesLib.api.lang.Locale;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.BaseComponent;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * A Users represents an abstract player of the game. There are implementations for every server mod
 * available.<br>
 */
public interface User {
    
    /**
     * @return the {@link Role} the user is assigned to
     */
    @Nonnull
    Role getRole();
    
    @Nonnull
    Locale getLocale();
    
    void setLocal(@Nonnull Locale local);
    
    /**
     * @return the prefix that should be displayed in chat and other location where the player name
     * is displayed
     */
    @Nonnull
    BaseComponent[] getPrefix();
    
    /**
     * @return the suffix that should be displayed in chat and other location where the player name
     * is displayed
     */
    @Nonnull
    BaseComponent[] getSuffix();
    
    /**
     * @return the display name of the user. doesn't need to be bound to the name of the underlying
     * player implementation
     */
    @Nonnull
    BaseComponent[] getDisplayName();
    
    /**
     * @return a unique identifier for that user.
     */
    @Nonnull
    UUID getUUID();
    
    /**
     * Send a message to this user.
     *
     * @param message the message to be send
     */
    void sendMessage(@Nonnull BaseComponent... message);
    
    /**
     * checks if that user has the desired permission.
     *
     * @param perm the permission object to check
     * @return whether or not the user has that permission
     */
    boolean hasPermission(@Nonnull Permission perm);
    
    /**
     * teleports the player to a location in the same world he is currently in
     *
     * @param loc the location to tp to
     */
    void teleport(Vector3D loc);
    
    /**
     * teleports the player to a location in a given world
     *
     * @param world the world to tp to
     * @param loc   the location to tp to
     */
    void teleport(String world, Vector3D loc);
    
    /**
     * teleports the player to that world (location is implementation detail, should be spawn if
     * possible)
     *
     * @param world the world to tp to
     */
    void teleport(String world);
    
    Vector3D getLocation();
}
