package me.minidigger.voxelgameslib.bukkit.block.metadata;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.block.metadata.EmptyBlockMetaData;

/**
 * Created by Martin on 05.02.2017.
 */
public class BukkitEmptyBlockMetaData implements EmptyBlockMetaData {

    private Block block;

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void setBlock(Block block) {
        this.block = block;
    }
}
