package me.minidigger.voxelgameslib.bukkit.user;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.config.GlobalConfig;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.role.RoleHandler;
import me.MiniDigger.VoxelGamesLib.api.user.User;

@Log
public class BukkitUser implements User {

    private Player player;
    private Role role;

    @Inject
    private RoleHandler roleHandler;
    @Inject
    private GlobalConfig config;

    public BukkitUser(Player player) {
        this.player = player;
    }

    public BukkitUser() {

    }

    @Nonnull
    @Override
    public Role getRole() {
        return role;
    }

    @Nonnull
    @Override
    public ChatMessage getPrefix() {
        return ChatMessage.fromLegacyFormat("");
    }

    @Nonnull
    @Override
    public ChatMessage getSuffix() {
        return ChatMessage.fromLegacyFormat("");
    }

    @Nonnull
    @Override
    public ChatMessage getDisplayName() {
        return ChatMessage.fromLegacyFormat(player.getDisplayName());
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
    public boolean hasPermission(@Nonnull Permission perm) {
        if (config.useRoleSystem) {
            return role.hasPermission(perm);
        } else {
            return player.hasPermission(perm.getString());
        }
    }

    @Override
    public void teleport(Vector3D loc) {
        player.teleport(new Location(player.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
    }

    @Override
    public void teleport(String world, Vector3D loc) {
        World w = Bukkit.getWorld(world);
        if (w != null) {
            player.teleport(new Location(w, loc.getX(), loc.getY(), loc.getZ()));
        } else {
            log.warning("Tries to teleport player " + getDisplayName() + " to world " + world + " which is not loaded!");
        }
    }
}
