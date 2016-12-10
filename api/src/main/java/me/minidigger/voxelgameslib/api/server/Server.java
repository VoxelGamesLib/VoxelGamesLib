package me.minidigger.voxelgameslib.api.server;

import java.util.List;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.user.ConsoleUser;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * Provides some general api features
 */
public interface Server {

    /**
     * @return a list with all online users
     */
    List<User> getOnlineUsers();

    /**
     * Sends a message to every {@link me.minidigger.voxelgameslib.api.user.User} on this server.
     *
     * @param message the message to be send
     */
    void broadcastMessage(@Nonnull BaseComponent... message);

    /**
     * Sends a message to every {@link me.minidigger.voxelgameslib.api.user.User} on this server.
     *
     * @param key  the message to be send
     * @param args the arguments for the key
     */
    void broadcastMessage(@Nonnull LangKey key, Object... args);

    /**
     * @return the console user for this server
     */
    ConsoleUser getConsoleUser();
}
