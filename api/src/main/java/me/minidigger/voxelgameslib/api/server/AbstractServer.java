package me.minidigger.voxelgameslib.api.server;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * Abstract implementation of a few basic functions of the server interface
 */
public abstract class AbstractServer implements Server {
    
    @Override
    public void broadcastMessage(@Nonnull LangKey key, Object... args) {
        for (User user : getOnlineUsers()) {
            Lang.msg(user, key, args);
        }
    }
    
    @Override
    public void broadcastMessage(@Nonnull BaseComponent... message) {
        for (User user : getOnlineUsers()) {
            user.sendMessage(message);
        }
    }
}
