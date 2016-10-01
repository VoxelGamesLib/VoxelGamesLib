package me.minidigger.voxelgameslib.bukkit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.MiniDigger.VoxelGamesLib.api.VoxelGamesLib;

public final class VoxelGamesLibBukkit extends JavaPlugin implements Listener {

    private VoxelGamesLib voxelGameLib = new VoxelGamesLib();

    @Override
    public void onEnable() {
        voxelGameLib.onEnable();

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        voxelGameLib.onDisable();
    }
}
