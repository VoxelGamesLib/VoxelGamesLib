package me.minidigger.voxelgameslib.api.bossbar;

import java.util.List;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.ImplementMe;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Represents the boss bar, that can be displayed to a number of players.
 */
public interface BossBar<T> extends ImplementMe<T> {

    /**
     * Returns the title of this boss bar
     *
     * @return the title of the bar
     */
    @Nonnull
    String getTitle();

    /**
     * Sets the title of this boss bar
     *
     * @param title the title of the bar
     */
    void setTitle(@Nonnull String title);

    /**
     * Returns the color of this boss bar
     *
     * @return the color of the bar
     */
    @Nonnull
    BossBarColor getColor();

    /**
     * Sets the color of this boss bar.
     *
     * @param color the color of the bar
     */
    void setColor(@Nonnull BossBarColor color);

    /**
     * Returns the style of this boss bar
     *
     * @return the style of the bar
     */
    @Nonnull
    BossBarStyle getStyle();

    /**
     * Sets the bar style of this boss bar
     *
     * @param style the style of the bar
     */
    void setStyle(@Nonnull BossBarStyle style);

    /**
     * Remove an existing modifier on this boss bar
     *
     * @param modifier the existing modifier to remove
     */
    void removeModifier(@Nonnull BossBarModifier modifier);

    /**
     * Add an optional modifier to this boss bar
     *
     * @param modifier an optional modifier to set on the boss bar
     */
    void addModifier(@Nonnull BossBarModifier modifier);

    /**
     * Returns whether this boss bar as the passed modifier set
     *
     * @param modifier the modifier to check
     * @return whether it has the modifier
     */
    boolean hasModifier(@Nonnull BossBarModifier modifier);

    /**
     * Sets the progress of the bar. Values should be between 0.0 (empty) and
     * 1.0 (full)
     *
     * @param progress the progress of the bar
     */
    void setProgress(double progress);

    /**
     * Returns the progress of the bar between 0.0 and 1.0
     *
     * @return the progress of the bar
     */
    double getProgress();

    /**
     * Adds the user to this boss bar causing it to display on their screen.
     *
     * @param user the user to add
     */
    void addUser(@Nonnull User user);

    /**
     * Removes the user from this boss bar causing it to be removed from their
     * screen.
     *
     * @param user the user to remove
     */
    void removeUser(@Nonnull User user);

    /**
     * Removes all users from this boss bar
     *
     * @see #removeUser(User)
     */
    void removeAll();

    /**
     * Returns all users viewing this boss bar
     *
     * @return a immutable list of users
     */
    @Nonnull
    List<User> getUsers();

    /**
     * Set if the boss bar is displayed to attached user.
     *
     * @param visible visible status
     */
    void setVisible(boolean visible);

    /**
     * Return if the boss bar is displayed to attached user.
     *
     * @return visible status
     */
    boolean isVisible();

}
