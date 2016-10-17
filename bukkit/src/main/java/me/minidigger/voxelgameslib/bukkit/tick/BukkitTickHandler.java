package me.minidigger.voxelgameslib.bukkit.tick;

import me.minidigger.voxelgameslib.bukkit.VoxelGamesLibBukkit;

import me.MiniDigger.VoxelGamesLib.api.tick.TickHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BukkitTickHandler extends TickHandler {
    
    @Inject
    private VoxelGamesLibBukkit plugin;
    
    @Override
    public void start() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
    }
}
