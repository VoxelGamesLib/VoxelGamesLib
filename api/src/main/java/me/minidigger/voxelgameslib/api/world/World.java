package me.minidigger.voxelgameslib.api.world;

import me.minidigger.voxelgameslib.api.ImplementMe;
import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.map.Vector3D;

/**
 * Represents a world on the server
 */
public interface World<T> extends ImplementMe<T> {

    /**
     * @return The name of this world
     */
    String getName();

    /**
     * Gets the block at the specified location
     *
     * @param location the location
     * @return the block at that location
     */
    Block getBlockAt(Vector3D location);
}
