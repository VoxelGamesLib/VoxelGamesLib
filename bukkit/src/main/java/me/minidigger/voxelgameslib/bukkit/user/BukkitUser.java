package me.minidigger.voxelgameslib.bukkit.user;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.Direction;
import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.RoleHandler;
import me.minidigger.voxelgameslib.api.user.AbstractUser;
import me.minidigger.voxelgameslib.api.user.GameMode;
import me.minidigger.voxelgameslib.api.utils.DirectionUtil;
import me.minidigger.voxelgameslib.bukkit.converter.GameModeConverter;
import me.minidigger.voxelgameslib.bukkit.item.BukkitItem;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.chat.ComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import lombok.extern.java.Log;

@Log
public class BukkitUser extends AbstractUser<Player> {

    private Player player;

    @Inject
    private RoleHandler roleHandler;
    @Inject
    private CommandHandler commandHandler;
    @Inject
    private GameModeConverter gameModeConverter;

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
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(@Nonnull BaseComponent... message) {
        //this is so ugly, but we live with that for now since we want to be compatible with bukkit and don't really need nms
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + ComponentSerializer.toString(message));
    }

    @Override
    public boolean hasPermission(@Nonnull Permission perm) {
        return super.hasPermission(perm) || player.hasPermission(perm.getString());
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
            log.warning("Tried to teleport player " + getData().getDisplayName() + " to world " + world + " which is not loaded!");
        }
    }

    @Override
    public void teleport(@Nonnull String world) {
        World w = Bukkit.getWorld(world);
        if (w != null) {
            Vector3D loc = new Vector3D(w.getSpawnLocation().getX(), w.getSpawnLocation().getY(), w.getSpawnLocation().getZ());
            player.teleport(new Location(w, loc.getX(), loc.getY(), loc.getZ()));
        } else {
            log.warning("Tried to teleport player " + getData().getDisplayName() + " to world " + world + " which is not loaded!");
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
        Item item = getInjector().getInstance(Item.class);
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
    public void setInventory(int slot, @Nonnull Item item) {
        player.getInventory().setItem(slot, ((BukkitItem) item).getImplementationType());
    }

    @Nonnull
    @Override
    public Item getInventory(int slot) {
        Item item = getInjector().getInstance(Item.class);
        //noinspection unchecked
        item.setImplementationType(player.getInventory().getItem(slot));
        return item;
    }

    @Override
    public void clearInventory() {
        player.getInventory().clear();
    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public void damage(double damage) {
        player.damage(damage);
    }

    @Override
    public void setHealth(double health) {
        player.setHealth(health);
    }

    @Override
    public double getHunger() {
        return player.getFoodLevel();
    }

    @Override
    public void setHunger(double hunger) {
        player.setHealth(hunger);
    }

    @Nonnull
    @Override
    public GameMode getGameMode() {
        return gameModeConverter.toVGL(player.getGameMode());
    }

    @Override
    public void setGameMode(GameMode mode) {
        player.setGameMode(gameModeConverter.fromVGL(mode));
    }

    @Override
    public Direction getFacingDirection() {
        return DirectionUtil.yawToDirection(player.getLocation().getYaw());
    }
}
