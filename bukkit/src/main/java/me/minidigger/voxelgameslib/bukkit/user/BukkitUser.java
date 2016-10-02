package me.minidigger.voxelgameslib.bukkit.user;

import org.bukkit.entity.Player;

import java.util.UUID;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.user.User;

public class BukkitUser implements User {

    private Player player;

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
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(@Nonnull ChatMessage message) {
        player.sendRawMessage(message.toRawMessage());
    }

    @Override
    public boolean hasPermission(@Nonnull String perm) {
        return player.hasPermission(perm);
    }
}
