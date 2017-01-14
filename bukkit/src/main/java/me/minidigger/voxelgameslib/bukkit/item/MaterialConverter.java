package me.minidigger.voxelgameslib.bukkit.item;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.convert.Converter;
import me.minidigger.voxelgameslib.api.item.Material;

/**
 * Created by Martin on 12.01.2017.
 */
@Singleton
public class MaterialConverter implements Converter<Material, org.bukkit.Material> {
    @Override
    public org.bukkit.Material fromVGL(Material material) {
        for (org.bukkit.Material mat : org.bukkit.Material.values()) {
            if (mat.getId() == material.getId()) {
                return mat;
            }
        }
        throw new IllegalArgumentException("No material for id " + material.getId() + "(" + material.name() + ")");
    }

    @Override
    public Material toVGL(org.bukkit.Material material) {
        return Material.fromId(material.getId());
    }
}
