package me.minidigger.voxelgameslib.bukkit.map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import java.util.ArrayList;
import java.util.List;

import me.MiniDigger.VoxelGamesLib.api.exception.MapException;
import me.MiniDigger.VoxelGamesLib.api.map.Map;
import me.MiniDigger.VoxelGamesLib.api.map.MapScanner;
import me.MiniDigger.VoxelGamesLib.api.map.Marker;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;

/**
 * Created by Martin on 04.10.2016.
 */
public class BukkitMapScanner extends MapScanner {

    @Override
    public List<Marker> searchForMarkers(Map map, Vector3D center, int range) {
        World world = Bukkit.getWorld(map.getWorldName());
        if (world == null) {
            throw new MapException("Could not find world " + map.getWorldName() + ". Is it loaded?");
        }

        List<Marker> markers = new ArrayList<>();

        int startX = (int) center.getX();
        int startY = (int) center.getZ();

        int minX = startX - range;
        int minZ = startY - range;

        int maxX = startX + range;
        int maxZ = startY + range;

        for (int x = minX; x <= maxX; x += 16) {
            for (int z = minZ; z <= maxZ; z += 16) {
                Chunk chunk = world.getChunkAt(x, z);
                for (BlockState te : chunk.getTileEntities()) {
                    if (te.getType() == Material.SKULL) {
                        Skull skull = (Skull) te;
                        if (skull.getSkullType() == SkullType.PLAYER) {
                            String markerData = skull.getOwningPlayer().getName();
                            markers.add(new Marker(new Vector3D(skull.getX(), skull.getY(), skull.getZ()), markerData));
                        }
                    }
                }
            }
        }

        return markers;
    }
}
