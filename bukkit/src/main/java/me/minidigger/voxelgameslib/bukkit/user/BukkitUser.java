package me.minidigger.voxelgameslib.bukkit.user;

import com.google.inject.Injector;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.config.GlobalConfig;
import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.role.RoleHandler;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;
import me.minidigger.voxelgameslib.bukkit.item.BukkitItem;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.chat.ComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import lombok.extern.java.Log;

@Log
public class BukkitUser implements User<Player> {
    
    private Player player;
    private Role role;
    @Nonnull
    private Locale locale = Locale.ENGLISH;
    
    @Inject
    private RoleHandler roleHandler;
    @Inject
    private GlobalConfig config;
    @Inject
    private CommandHandler commandHandler;
    @Inject
    private Injector injector;
    
    @Override
    public void setImplementationType(@Nonnull Player p) {
        player = p;
    }
    
    @Nonnull
    @Override
    public Player getImplementationType() {
        return player;
    }
    
    @Override
    public void executeCommand(String cmd) {
        if (!commandHandler.executeCommand(this, cmd)) {
            Bukkit.dispatchCommand(player, cmd);
        }
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
    public void setLocale(@Nonnull Locale locale) {
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
    public UUID getUuid() {
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
    public void teleport(@Nonnull Vector3D loc) {
        player.teleport(new Location(player.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
    }
    
    @Override
    public void teleport(@Nonnull String world, @Nonnull Vector3D loc) {
        World w = Bukkit.getWorld(world);
        if (w != null) {
            player.teleport(new Location(w, loc.getX(), loc.getY(), loc.getZ()));
        } else {
            log.warning("Tried to teleport player " + ChatUtil.toPlainText(getDisplayName()) + " to world " + world + " which is not loaded!");
        }
    }
    
    @Override
    public void teleport(@Nonnull String world) {
        World w = Bukkit.getWorld(world);
        if (w != null) {
            Vector3D loc = new Vector3D(w.getSpawnLocation().getX(), w.getSpawnLocation().getY(), w.getSpawnLocation().getZ());
            player.teleport(new Location(w, loc.getX(), loc.getY(), loc.getZ()));
        } else {
            log.warning("Tried to teleport player " + ChatUtil.toPlainText(getDisplayName()) + " to world " + world + " which is not loaded!");
        }
    }
    
    @Nonnull
    @Override
    public Vector3D getLocation() {
        return new Vector3D(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }
    
    @Nonnull
    @Override
    public String getWorld() {
        return player.getWorld().getName();
    }
    
    @Override
    public void setItemInHand(@Nonnull Hand hand, @Nonnull Item item) {
        if (hand == Hand.MAINHAND) {
            player.getInventory().setItemInMainHand(((BukkitItem) item).getImplementationType());
        } else {
            player.getInventory().setItemInOffHand(((BukkitItem) item).getImplementationType());
        }
    }
    
    @Nonnull
    @Override
    public Item getItemInHand(@Nonnull Hand hand) {
        Item item = injector.getInstance(Item.class);
        if (hand == Hand.MAINHAND) {
            //noinspection unchecked
            item.setImplementationType(player.getInventory().getItemInMainHand());
            return item;
        } else {
            //noinspection unchecked
            item.setImplementationType(player.getInventory().getItemInOffHand());
            return item;
        }
    }
    
    @Override
    public void setIventory(int slot, @Nonnull Item item) {
        player.getInventory().setItem(slot, ((BukkitItem) item).getImplementationType());
    }
    
    @Nonnull
    @Override
    public Item getInventory(int slot) {
        Item item = injector.getInstance(Item.class);
        //noinspection unchecked
        item.setImplementationType(player.getInventory().getItem(slot));
        return item;
    }
    
    @Nonnull
    @Override
    public Injector getInjector() {
        return injector;
    }
    
    @Override
    public void clearInventory() {
        player.getInventory().clear();
    }
}
