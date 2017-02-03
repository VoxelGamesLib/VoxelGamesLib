package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import java.util.UUID;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.ImplementMe;
import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

import jskills.IPlayer;
import jskills.ISupportPartialPlay;
import jskills.ISupportPartialUpdate;

/**
 * A Users represents an abstract player of the game. There are implementations for every server mod
 * available.<br>
 */
public interface User<T> extends ImplementMe<T>, IPlayer, ISupportPartialPlay, ISupportPartialUpdate {

    /**
     * @return the user data for this user
     */
    @Nonnull
    UserData getData();

    /**
     * Updates the data object for this user. Should not really be used!
     *
     * @param data the new data object
     */
    void setData(UserData data);

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
    void setInventory(int slot, @Nonnull Item item);

    /**
     * @param slot the slot
     * @return the item in the speficied inventory slot
     */
    @Nonnull
    Item getInventory(int slot);

    /**
     * Should be called to mark the display name as dirty. will cause getDisplayName to generate a
     * fresh version the next time its executed
     */
    void markDisplayNameAsDirty();

    /**
     * @return the injector that was used to create this user
     */
    @Nonnull
    Injector getInjector();

    /**
     * Lets the player execute a command.
     *
     * @param cmd the command string to execute
     */
    void executeCommand(String cmd);

    /**
     * Clears the inventory of the user
     */
    void clearInventory();

    /**
     * @return the health of the user
     */
    double getHealth();

    /**
     * damages the user
     *
     * @param damage the damage that will be applied
     */
    void damage(double damage);

    /**
     * sets the heal of the user to the given value
     *
     * @param health the new health value
     */
    void setHealth(double health);

    /**
     * @return the hunger level of the user
     */
    double getHunger();

    /**
     * changes the hunger level of the user
     *
     * @param hunger the new hunger level for the user
     */
    void setHunger(double hunger);

    /**
     * sets a new gamemode for the user
     *
     * @param mode the new mode
     */
    void setGameMode(GameMode mode);

    /**
     * @return the gamemode the player is in
     */
    @Nonnull
    GameMode getGameMode();

    /**
     * returns the display name of the user, consists of prefix, name and suffix. <b>this method
     * caches the display name. if you changed the prefix, suffix or display name of the user, you
     * need to mark the display name as dirty!</b>
     *
     * @return the display name of the user, consists of prefix, name and suffix.
     */
    BaseComponent[] getDisplayName();
}
