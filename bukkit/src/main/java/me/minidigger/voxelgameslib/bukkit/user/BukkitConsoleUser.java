package me.minidigger.voxelgameslib.bukkit.user;

import com.google.inject.Injector;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.ItemBuilder;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.user.ConsoleUser;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by Martin on 02.10.2016.
 */
public class BukkitConsoleUser implements ConsoleUser {
    
    private ConsoleCommandSender user = Bukkit.getConsoleSender();
    
    @Inject
    private Injector injector;
    
    @Override
    public void setPlayerObject(@Nonnull Object playerObject) {
        // unused
    }
    
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
    public void setLocale(@Nonnull Locale locale) {
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
    public UUID getUuid() {
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
    public void teleport(@Nonnull Vector3D loc) {
        // ignore
    }
    
    @Override
    public void teleport(@Nonnull String world, @Nonnull Vector3D loc) {
        // ignore
    }
    
    @Override
    public void teleport(@Nonnull String world) {
        // ignore
    }
    
    @Nonnull
    @Override
    public Vector3D getLocation() {
        return new Vector3D(0, 0, 0);
    }
    
    @Nonnull
    @Override
    public String getWorld() {
        return "world";
    }
    
    @Override
    public void setItemInHand(@Nonnull Hand hand, @Nonnull Item item) {
        // ignore
    }
    
    @Nonnull
    @Override
    public Item getItemInHand(@Nonnull Hand hand) {
        return new ItemBuilder(Material.AIR, injector).build();
    }
    
    @Override
    public void setIventory(int slot, @Nonnull Item item) {
        // ignore
    }
    
    @Nonnull
    @Override
    public Item getInventory(int slot) {
        return new ItemBuilder(Material.AIR, injector).build();
    }
    
    @Nonnull
    @Override
    public Injector getInjector() {
        return injector;
    }
}
