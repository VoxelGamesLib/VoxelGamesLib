package me.minidigger.voxelgameslib.bukkit.user;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import org.bukkit.command.ConsoleCommandSender;

import java.util.UUID;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.user.ConsoleUser;

/**
 * Created by Martin on 02.10.2016.
 */
public class BukkitConsoleUser implements ConsoleUser {

    private ConsoleCommandSender user;

    @Nonnull
    @Override
    public Role getRole() {
        return null;
    }

    @Nonnull
    @Override
    public ChatMessage getPrefix() {
        return null;
    }

    @Nonnull
    @Override
    public ChatMessage getSuffix() {
        return null;
    }

    @Nonnull
    @Override
    public ChatMessage getDisplayName() {
        return null;
    }

    @Nonnull
    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public void sendMessage(@Nonnull BaseComponent... message) {
        //TODO check if this actually works
        user.sendRawMessage(ComponentSerializer.toString(message));
    }

    @Override
    public boolean hasPermission(@Nonnull Permission perm) {
        return true;
    }

    @Override
    public void teleport(Vector3D loc) {
        // ignore
    }

    @Override
    public void teleport(String world, Vector3D loc) {
        // ignore
    }

    @Override
    public void teleport(String world) {

    }

    public void setUser(ConsoleCommandSender user) {
        this.user = user;
    }
}
