package me.minidigger.voxelgameslib.bukkit.world;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.world.WorldHandler;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

/**
 * Created by Martin on 07.10.2016.
 */
public class BukkitWorldHandler extends WorldHandler {
    
    @Override
    public void loadWorld(@Nonnull Map map) {
        super.loadWorld(map);
        
        loadLocalWorld(map.getWorldName());
    }
    
    @Override
    public void unloadWorld(@Nonnull Map map) {
        unloadLocalWorld(map.getWorldName());
        super.unloadWorld(map);
    }
    
    @Override
    public void loadLocalWorld(@Nonnull String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(World.Environment.NORMAL); //TODO do we need support for environment in maps?
        wc.generateStructures(false);
        wc.type(WorldType.NORMAL);
        wc.generator(new CleanRoomChunkGenerator());
        wc.generatorSettings("");
        wc.createWorld();
    }
    
    @Override
    public void unloadLocalWorld(@Nonnull String name) {
        Bukkit.unloadWorld(name, false);
    }
}
