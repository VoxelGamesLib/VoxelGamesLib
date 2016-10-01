package me.MiniDigger.VoxelGamesLib.api.message;

import javax.annotation.Nonnull;

/**
 * An abstract representation of a message that can be displayed in the chat. There are
 * implementations for every server mod available.
 */
public class ChatMessage {

    /**
     * Converts this message to the raw json format
     *
     * @return this message in the raw json format
     */
    @Nonnull
    public String toRawMessage() {
        return "<todo>";
    }
}
