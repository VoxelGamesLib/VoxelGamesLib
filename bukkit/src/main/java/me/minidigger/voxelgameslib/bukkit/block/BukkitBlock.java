package me.minidigger.voxelgameslib.bukkit.block;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.bukkit.item.MaterialConverter;
import me.minidigger.voxelgameslib.bukkit.world.VectorConverter;

/**
 * Created by Martin on 08.01.2017.
 */
public class BukkitBlock implements Block<org.bukkit.block.Block> {

    @Inject
    private MaterialConverter materialConverter;
    @Inject
    private VectorConverter vectorConverter;

    private org.bukkit.block.Block block;

    @Override
    public Material getMaterial() {
        return materialConverter.toVGL(block.getType());
    }

    @Override
    public void setMaterial(Material material) {
        block.setType(materialConverter.fromVGL(material));
    }

    @Override
    public byte getVariant() {
        return block.getData();
    }

    @Override
    public void setVariant(byte variant) {
        block.setData(variant);
    }

    @Override
    public Vector3D getLocation() {
        return vectorConverter.toVGL(block.getLocation().toVector());
    }

    @Override
    public String getWorld() {
        return block.getWorld().getName();
    }

    @Nonnull
    @Override
    public org.bukkit.block.Block getImplementationType() {
        return block;
    }

    @Override
    public void setImplementationType(@Nonnull org.bukkit.block.Block block) {
        this.block = block;
    }
}
