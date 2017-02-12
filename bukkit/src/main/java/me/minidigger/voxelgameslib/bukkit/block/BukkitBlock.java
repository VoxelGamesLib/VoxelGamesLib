package me.minidigger.voxelgameslib.bukkit.block;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.block.Direction;
import me.minidigger.voxelgameslib.api.block.metadata.BlockMetaData;
import me.minidigger.voxelgameslib.api.block.metadata.BlockMetaFactory;
import me.minidigger.voxelgameslib.api.exception.VoxelGameLibException;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.world.World;
import me.minidigger.voxelgameslib.bukkit.converter.MaterialConverter;
import me.minidigger.voxelgameslib.bukkit.converter.VectorConverter;

import lombok.extern.java.Log;

/**
 * Created by Martin on 08.01.2017.
 */
@Log
public class BukkitBlock implements Block<org.bukkit.block.Block> {

    @Inject
    private MaterialConverter materialConverter;
    @Inject
    private VectorConverter vectorConverter;
    @Inject
    private BlockMetaFactory blockMetaFactory;
    @Inject
    private Server server;

    private org.bukkit.block.Block block;
    private BlockMetaData blockMetaData;

    @Override
    public Material getMaterial() {
        return materialConverter.toVGL(block.getType());
    }

    @Override
    public void setMaterial(Material material) {
        System.out.println("set type to " + materialConverter.fromVGL(material) + " " + block.getLocation());
        block.setType(materialConverter.fromVGL(material), true);
        System.out.println("type now " + block.getType());
        blockMetaData = blockMetaFactory.createMetaData(this);
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

    @Override
    public BlockMetaData getMetaData() {
        return blockMetaData;
    }

    @Nonnull
    @Override
    public org.bukkit.block.Block getImplementationType() {
        return block;
    }

    @Override
    public void setImplementationType(@Nonnull org.bukkit.block.Block block) {
        this.block = block;
        blockMetaData = blockMetaFactory.createMetaData(this);
    }

    @Override
    public Block getRelative(Direction dir, int amount) {
        Optional<World> world = server.getWorld(getWorld());
        if (!world.isPresent()) {
            throw new VoxelGameLibException(getWorld() + " is not loaded! Can't get relative block!");
        }
        return world.get().getBlockAt(getLocation().add(dir.getVector().multiply(amount)));
    }
}
