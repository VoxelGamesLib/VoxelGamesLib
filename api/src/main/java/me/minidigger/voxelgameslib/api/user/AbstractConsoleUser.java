package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.ItemBuilder;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * Abstract implementation of the console user interface that deals with most stuff
 *
 * @param <T> the implementation type
 */
public abstract class AbstractConsoleUser<T> implements ConsoleUser<T> {

    @Inject
    private Injector injector;

    @Nonnull
    @Override
    public UserData getData() {
        throw new UnsupportedOperationException("Console has no data!");
    }

    @Override
    public void setData(UserData data) {
        throw new UnsupportedOperationException("Console has no data!");
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
    public void setInventory(int slot, @Nonnull Item item) {
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

    @Override
    public void clearInventory() {
        // ignore
    }

    @Override
    public double getHealth() {
        return 9001; // ITS OVER 9000 !!!!
    }

    @Override
    public void damage(double damage) {
        // can't touch this
    }

    @Override
    public void setHealth(double health) {
        // ignore
    }

    @Override
    public double getHunger() {
        return 0;
    }

    @Override
    public void setHunger(double hunger) {
        // ignore
    }

    @Override
    public void setGameMode(GameMode mode) {
        // ignore
    }

    @Nonnull
    @Override
    public GameMode getGameMode() {
        return GameMode.CREATIVE;
    }


    /* elo stuff */
    @Override
    public double getPartialPlayPercentage() {
        return 1.0;
    }

    @Override
    public double getPartialUpdatePercentage() {
        return 1.0;
    }
}
