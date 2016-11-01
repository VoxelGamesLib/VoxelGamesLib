package me.minidigger.voxelgameslib.bukkit.item;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.item.Material;

/**
 * Created by Martin on 05.10.2016.
 */
public class BukkitMaterial {
    
    @Nonnull
    public static Material fromMaterial(@Nonnull org.bukkit.Material material) {
        return Material.fromId(material.getId());
    }
    
    @Nonnull
    public static org.bukkit.Material toMaterial(@Nonnull Material material) {
        for (org.bukkit.Material mat : org.bukkit.Material.values()) {
            if (mat.getId() == material.getId()) {
                return mat;
            }
        }
        throw new IllegalArgumentException("No material for id " + material.getId() + "(" + material.name() + ")");
    }
}
