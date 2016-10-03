package me.MiniDigger.VoxelGamesLib.api.user;

import java.util.UUID;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.Role;

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
     * @return the prefix that should be displayed in chat and other location where the player name
     * is displayed
     */
    @Nonnull
    ChatMessage getPrefix();

    /**
     * @return the suffix that should be displayed in chat and other location where the player name
     * is displayed
     */
    @Nonnull
    ChatMessage getSuffix();

    /**
     * @return the display name of the user. doesn't need to be bound to the name of the underlaying
     * player implementation
     */
    @Nonnull
    ChatMessage getDisplayName();

    /**
     * @return a unique identifier for that user.
     */
    @Nonnull
    UUID getUUID();

    /**
     * Send a {@link ChatMessage} to this user.
     *
     * @param message the {@link ChatMessage} to be send
     */
    void sendMessage(@Nonnull ChatMessage message);

    /**
     * checks if that user has the desired permission.
     *
     * @param perm the permission object to check
     * @return whether or not the user has that permission
     */
    boolean hasPermission(@Nonnull Permission perm);
}
