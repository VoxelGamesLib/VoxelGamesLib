package me.minidigger.voxelgameslib.api.block.metadata;

import me.minidigger.voxelgameslib.api.block.Direction;

/**
 * Metadata wrapper for block which might be rotated
 */
public interface DirectionMetaData {

    /**
     * @return the direction this block is facing to
     */
    Direction getDirection();

    /**
     * Changes the direction this block is facing to
     *
     * @param direction the new direction
     */
    void setDirection(Direction direction);
}
