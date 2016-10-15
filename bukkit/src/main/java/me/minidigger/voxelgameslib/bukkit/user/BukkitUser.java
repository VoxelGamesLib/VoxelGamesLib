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
import me.MiniDigger.VoxelGamesLib.api.lang.Locale;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.role.RoleHandler;
import me.MiniDigger.VoxelGamesLib.api.user.User;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.ComponentBuilder;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.chat.ComponentSerializer;

@Log
public class BukkitUser implements User {

    private Player player;
    private Role role;
    private Locale locale = Locale.ENGLISH;

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
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocal(@Nonnull Locale locale) {
        this.locale = locale;
    }

    @Nonnull
    @Override
    public BaseComponent[] getPrefix() {
        return new ComponentBuilder("PREFIX").create();
    }

    @Nonnull
    @Override
    public BaseComponent[] getSuffix() {
        return new ComponentBuilder("SUFFIX").create();
    }

    @Nonnull
    @Override
    public BaseComponent[] getDisplayName() {
        return new ComponentBuilder(player.getDisplayName()).create();
    }

    @Nonnull
    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(@Nonnull BaseComponent... message) {
        //TODO this is so ugly...
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + ComponentSerializer.toString(message));
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

    @Override
    public void teleport(String world) {

    }
}
