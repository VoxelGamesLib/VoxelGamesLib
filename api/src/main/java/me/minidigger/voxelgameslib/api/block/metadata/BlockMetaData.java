package me.minidigger.voxelgameslib.api.block.metadata;

import me.minidigger.voxelgameslib.api.block.Block;

/**
 * A block meta data is a wrapper for additional data a block might have
 */
public interface BlockMetaData {

    /**
     * @return The block this meta data object provides more info for
     */
    Block getBlock();

    /**
     * Sets the underlying block, should only be called by the meta data factory!
     *
     * @param block the new block
     */
    void setBlock(Block block);
}
