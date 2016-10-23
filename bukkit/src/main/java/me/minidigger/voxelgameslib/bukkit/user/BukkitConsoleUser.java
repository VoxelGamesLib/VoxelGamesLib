package me.minidigger.voxelgameslib.bukkit.user;

import com.google.inject.Injector;

import org.bukkit.command.ConsoleCommandSender;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.item.Hand;
import me.MiniDigger.VoxelGamesLib.api.item.Item;
import me.MiniDigger.VoxelGamesLib.api.item.ItemBuilder;
import me.MiniDigger.VoxelGamesLib.api.item.Material;
import me.MiniDigger.VoxelGamesLib.api.lang.Locale;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.user.ConsoleUser;
import me.MiniDigger.VoxelGamesLib.api.utils.ChatUtil;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * Created by Martin on 02.10.2016.
 */
public class BukkitConsoleUser implements ConsoleUser {

    private ConsoleCommandSender user;

    @Inject
    private Injector injector;

    @Nonnull
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Nonnull
    @Override
    public Locale getLocale() {
        return Locale.ENGLISH;
    }

    @Override
    public void setLocal(@Nonnull Locale local) {
        // ignored
    }

    @Nonnull
    @Override
    public BaseComponent[] getPrefix() {
        return new ComponentBuilder("").create();
    }

    @Nonnull
    @Override
    public BaseComponent[] getSuffix() {
        return new ComponentBuilder("").create();
    }

    @Nonnull
    @Override
    public BaseComponent[] getDisplayName() {
        return new ComponentBuilder("ConsoleUser").create();
    }

    @Nonnull
    @Override
    public UUID getUUID() {
        return UUID.nameUUIDFromBytes("ConsoleUser".getBytes());
    }

    @Override
    public void sendMessage(@Nonnull BaseComponent... message) {
        user.sendMessage(ChatUtil.toPlainText(message));
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
        // ignore
    }

    @Override
    public Vector3D getLocation() {
        return new Vector3D(0, 0, 0);
    }

    public void setUser(ConsoleCommandSender user) {
        this.user = user;
    }

    @Override
    public String getWorld() {
        return "world";
    }

    @Override
    public void setItemInHand(Hand hand, Item item) {
        // ignore
    }

    @Override
    public Item getItemInHand(Hand hand) {
        return new ItemBuilder(Material.AIR, injector).build();
    }

    @Override
    public void setIventory(int slot, Item item) {
        // ignore
    }

    @Override
    public Item getInventory(int slot) {
        return new ItemBuilder(Material.AIR, injector).build();
    }

    @Override
    public Injector getInjector() {
        return injector;
    }
}
