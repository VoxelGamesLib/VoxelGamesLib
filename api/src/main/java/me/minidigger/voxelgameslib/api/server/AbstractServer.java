package me.minidigger.voxelgameslib.api.server;

import java.util.Optional;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.world.World;
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
        Lang.msg(getConsoleUser(), key, args);
    }

    @Override
    public void broadcastMessage(@Nonnull BaseComponent... message) {
        for (User user : getOnlineUsers()) {
            user.sendMessage(message);
        }
        getConsoleUser().sendMessage(message);
    }

    @Override
    public int broadcastMessage(@Nonnull Role role, @Nonnull LangKey key, Object... args) {
        int i = 0;
        for (User user : getOnlineUsers()) {
            if (role.equals(user.getData().getRole())) {
                i++;
                Lang.msg(user, key, args);
            }
        }
        return i;
    }

    @Override
    public int broadcastMessage(@Nonnull Role role, @Nonnull BaseComponent... message) {
        int i = 0;
        for (User user : getOnlineUsers()) {
            if (role.equals(user.getData().getRole())) {
                i++;
                user.sendMessage(message);
            }
        }
        return i;
    }

    @Override
    public Optional<World> getWorld(String world) {
        for (World w : getWorlds()) {
            if (w.getName().equalsIgnoreCase(world)) {
                return Optional.of(w);
            }
        }

        return Optional.empty();
    }
}
