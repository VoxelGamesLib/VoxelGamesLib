package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import me.minidigger.voxelgameslib.api.ImplementMe;
import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

import jskills.IPlayer;
import jskills.ISupportPartialPlay;
import jskills.ISupportPartialUpdate;
import jskills.Rating;

/**
 * A Users represents an abstract player of the game. There are implementations for every server mod
 * available.<br>
 */
@MappedSuperclass
public interface User<T> extends ImplementMe<T>, IPlayer, ISupportPartialPlay, ISupportPartialUpdate {

    /**
     * @return the {@link Role} the user is assigned to
     */
    @Nonnull
    @Enumerated(EnumType.STRING)
    Role getRole();

    void setRole(Role role);

    /**
     * @return the locale this player whished to receive messages in
     */
    @Nonnull
    @ManyToOne
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
    @Transient
    BaseComponent[] getPrefix();

    /**
     * @return the suffix that should be displayed in chat and other location where the player name
     * is displayed
     */
    @Nonnull
    @Transient
    BaseComponent[] getSuffix();

    /**
     * @return the display name of the user. doesn't need to be bound to the name of the underlying
     * player implementation
     */
    @Nonnull
    @Transient
    BaseComponent[] getDisplayName();

    /**
     * @return a unique identifier for that user.
     */
    @Id
    @Nonnull
    UUID getUuid();

    /**
     * should not be used, only for db stuff
     *
     * @param id should not be used, only for db stuff
     * @deprecated should not be used, only for db stuff
     */
    @Deprecated
    void setUuid(UUID id);

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
    @Transient
    Vector3D getLocation();

    /**
     * @return the world this user is currently playing on
     */
    @Nonnull
    @Transient
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
    @Transient
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
    @Transient
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
    @Transient
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
    @Transient
    GameMode getGameMode();

    /**
     * @param mode the mode to get the rating for
     * @return the rating of this player for gamemode mode. will return default values if not
     * present
     */
    Rating getRating(me.minidigger.voxelgameslib.api.game.GameMode mode);

    /**
     * Saves a rating for this users. will override existing ratings
     *
     * @param mode   the mode the rating was achieved in
     * @param rating the new rating
     */
    void saveRating(me.minidigger.voxelgameslib.api.game.GameMode mode, Rating rating);

    /**
     * @return all ratings for this player
     */
    @Transient
    //TODO figure out how to store this
    Map<me.minidigger.voxelgameslib.api.game.GameMode, Rating> getRatings();
}
