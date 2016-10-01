package me.MiniDigger.VoxelGamesLib.api.user;

import java.util.UUID;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.role.Role;

/**
 * A Users represents an abstract player of the game. There are implementations for every server mod
 * available.<br>
 */
public interface User {

    /**
     * @return the {@link Role} the user is assigned to
     */
    Role getRole();

    /**
     * @return the prefix that should be displayed in chat and other location where the player name
     * is displayed
     */
    ChatMessage getPrefix();

    /**
     * @return the suffix that should be displayed in chat and other location where the player name
     * is displayed
     */
    ChatMessage getSuffix();

    /**
     * @return the display name of the user. doesn't need to be bound to the name of the underlaying
     * player implementation
     */
    ChatMessage getDisplayName();

    /**
     * @return a unique identifier for that user.
     */
    UUID getUUID();

    /**
     * Send a {@link ChatMessage} to this user.
     *
     * @param message the {@link ChatMessage} to be send
     */
    void sendMessage(@Nonnull ChatMessage message);

}
