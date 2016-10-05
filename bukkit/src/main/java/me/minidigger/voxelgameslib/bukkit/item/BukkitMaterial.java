package me.minidigger.voxelgameslib.bukkit.item;

import me.MiniDigger.VoxelGamesLib.api.item.Material;

/**
 * Created by Martin on 05.10.2016.
 */
public class BukkitMaterial {

    public static Material fromMaterial(org.bukkit.Material material) {
        return Material.fromId(material.getId());
    }

    public static org.bukkit.Material toMaterial(Material material) {
        for (org.bukkit.Material mat : org.bukkit.Material.values()) {
            if (mat.getId() == material.getId()) {
                return mat;
            }
        }
        throw new IllegalArgumentException("No material for id " + material.getId() + "(" + material.name() + ")");
    }
}
