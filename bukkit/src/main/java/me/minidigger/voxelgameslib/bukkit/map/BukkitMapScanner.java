package me.minidigger.voxelgameslib.bukkit.map;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.exception.MapException;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.map.ChestMarker;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.map.MapScanner;
import me.minidigger.voxelgameslib.api.map.Marker;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.bukkit.util.FaceUtil;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;

import lombok.extern.java.Log;

/**
 * Created by Martin on 04.10.2016.
 */
@Log
public class BukkitMapScanner extends MapScanner {
    
    @Inject
    private Injector injector;
    
    @Override
    public void searchForMarkers(@Nonnull Map map, @Nonnull Vector3D center, int range) {
        World world = Bukkit.getWorld(map.getWorldName());
        if (world == null) {
            throw new MapException("Could not find world " + map.getWorldName() + ". Is it loaded?");
        }
        
        List<Marker> markers = new ArrayList<>();
        List<ChestMarker> chestMarkers = new ArrayList<>();
        
        int startX = (int) center.getX();
        int startY = (int) center.getZ();
        
        int minX = Math.min(startX - range, startX + range);
        int minZ = Math.min(startY - range, startY + range);
        
        int maxX = Math.max(startX - range, startX + range);
        int maxZ = Math.max(startY - range, startY + range);
        
        for (int x = minX; x <= maxX; x += 16) {
            for (int z = minZ; z <= maxZ; z += 16) {
                Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
                for (BlockState te : chunk.getTileEntities()) {
                    if (te.getType() == Material.SKULL) {
                        Skull skull = (Skull) te;
                        if (skull.getSkullType() == SkullType.PLAYER) {
                            if (skull.getOwningPlayer() != null) {
                                String markerData = skull.getOwningPlayer().getName();
                                if (markerData == null) {
                                    log.warning("owning player name null?!");
                                    markerData = skull.getOwner();
                                }
                                markers.add(new Marker(new Vector3D(skull.getX(), skull.getY(), skull.getZ()), FaceUtil.faceToYaw(skull.getRotation()), markerData));
                            } else {
                                log.warning("unknown owner");
                            }
                        }
                    } else if (te.getType() == Material.CHEST) {
                        Chest chest = (Chest) te;
                        String name = chest.getBlockInventory().getName();
                        Item[] items = new Item[chest.getBlockInventory().getStorageContents().length];
                        for (int i = 0; i < items.length; i++) {
                            ItemStack is = chest.getBlockInventory().getItem(i);
                            Item item = injector.getInstance(Item.class);
                            if (is == null) {
                                //noinspection unchecked
                                item.setImplementationType(new ItemStack(Material.AIR));
                                items[i] = item;
                            } else {
                                //noinspection unchecked
                                item.setImplementationType(is);
                                items[i] = item;
                            }
                        }
                        chestMarkers.add(new ChestMarker(new Vector3D(chest.getX(), chest.getY(), chest.getZ()), name, items));
                    }
                }
            }
        }
        
        map.setMarkers(markers);
        map.setChestMarkers(chestMarkers);
    }
}
