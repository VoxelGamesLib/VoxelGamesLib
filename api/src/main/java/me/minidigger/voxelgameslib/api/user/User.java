package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import java.util.UUID;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

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
    
    /**
     * @return the locale this player whished to receive messages in
     */
    @Nonnull
    Locale getLocale();
    
    /**
     * sets the preferred locale of this user
     *
     * @param locale the perferred locale of this user
     */
    void setLocale(@Nonnull Locale locale);
    
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
    UUID getUuid();
    
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
    void teleport(@Nonnull Vector3D loc);
    
    /**
     * teleports the player to a location in a given world
     *
     * @param world the world to tp to
     * @param loc   the location to tp to
     */
    void teleport(@Nonnull String world, @Nonnull Vector3D loc);
    
    /**
     * teleports the player to that world (location is implementation detail, should be spawn if
     * possible)
     *
     * @param world the world to tp to
     */
    void teleport(@Nonnull String world);
    
    /**
     * @return the location this user is located at in his world
     */
    @Nonnull
    Vector3D getLocation();
    
    /**
     * @return the world this user is currently playing on
     */
    @Nonnull
    String getWorld();
    
    /**
     * sets the item the user holds in his hand
     *
     * @param hand the hand which the item should be held in
     * @param item the item to hold
     */
    void setItemInHand(@Nonnull Hand hand, @Nonnull Item item);
    
    /**
     * gets the item the user is holding in the specified hand
     *
     * @param hand the hand which item should be returned
     * @return the item in the specified hand
     */
    @Nonnull
    Item getItemInHand(@Nonnull Hand hand);
    
    /**
     * sets a item in the inventory of the user
     *
     * @param slot the slot to change
     * @param item the new item that should be placed in that slot
     */
    void setIventory(int slot, @Nonnull Item item);
    
    /**
     * @param slot the slot
     * @return the item in the speficied inventory slot
     */
    @Nonnull
    Item getInventory(int slot);
    
    /**
     * @return the injector that was used to create this user
     */
    @Nonnull
    Injector getInjector();
    
    /**
     * sets the player object that is provided by the server implementation and used by
     * implementations of this interface
     *
     * @param playerObject the player object to set
     */
    void setPlayerObject(@Nonnull Object playerObject);
    
    /**
     * @return the server implementations object for this user
     */
    Object getPlayerObject();
    
    /**
     * Lets the player execute a command.
     *
     * @param cmd the command string to execute
     */
    void executeCommand(String cmd);
}
