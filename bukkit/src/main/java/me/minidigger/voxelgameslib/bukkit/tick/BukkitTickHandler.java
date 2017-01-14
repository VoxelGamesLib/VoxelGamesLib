package me.minidigger.voxelgameslib.bukkit.tick;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.tick.TickHandler;
import me.minidigger.voxelgameslib.bukkit.VoxelGamesLibBukkit;

@Singleton
public class BukkitTickHandler extends TickHandler {

    @Inject
    private VoxelGamesLibBukkit plugin;

    @Override
    public void start() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
    }
}
