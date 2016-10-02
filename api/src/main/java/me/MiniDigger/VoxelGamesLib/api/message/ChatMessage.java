package me.MiniDigger.VoxelGamesLib.api.message;

import javax.annotation.Nonnull;

/**
 * An abstract representation of a message that can be displayed in the chat. There are
 * implementations for every server mod available.
 */
public class ChatMessage {

    private String temp;

    /**
     * Converts this message to the raw json format
     *
     * @return this message in the raw json format
     */
    @Nonnull
    public String toRawMessage() {
        return temp;
    }

    /**
     * Converts a string into a chat message, using the legacy chat format.
     *
     * @param message the message, in legacy format
     * @return the converted message
     */
    @Nonnull
    public static ChatMessage fromLegacyFormat(@Nonnull String message) {
        ChatMessage msg = new ChatMessage();
        msg.temp = message;
        return msg;
    }
}
