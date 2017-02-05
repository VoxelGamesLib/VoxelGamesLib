package me.minidigger.voxelgameslib.bukkit.world;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.world.World;
import me.minidigger.voxelgameslib.bukkit.converter.BlockConverter;
import me.minidigger.voxelgameslib.bukkit.converter.VectorConverter;

/**
 * Created by Martin on 04.02.2017.
 */
public class BukkitWorld implements World<org.bukkit.World> {

    @Inject
    private VectorConverter vectorConverter;
    @Inject
    private BlockConverter blockConverter;

    private org.bukkit.World world;

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public Block getBlockAt(Vector3D location) {
        return blockConverter.toVGL(world.getBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ()));
    }

    @Nonnull
    @Override
    public org.bukkit.World getImplementationType() {
        return world;
    }

    @Override
    public void setImplementationType(@Nonnull org.bukkit.World world) {
        this.world = world;
    }
}
