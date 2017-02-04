package me.minidigger.voxelgameslib.api.block.metadata;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.block.Block;

/**
 * Factory which is capeable of creating new blockmetadata instance
 */
@Singleton
public class BlockMetaFactory {

    @Inject
    private Injector injector;

    /**
     * Create a new block meta data instance for a spefied block.
     * if no suiting instance is found, it will return an empty one.
     *
     * @param block the block to get the meta data for
     * @return the new meta data instance
     */
    public BlockMetaData createMetaData(Block block) {
        switch (block.getMaterial()) {
            case SIGN:
            case WALL_SIGN:
            case STANDING_SIGN:
                return instance(SignMetaData.class, block);
            default:
                return instance(EmptyBlockMetaData.class, block);
        }
    }

    private BlockMetaData instance(Class<? extends BlockMetaData> clazz, Block block) {
        BlockMetaData metaData = injector.getInstance(clazz);
        metaData.setBlock(block);
        return metaData;
    }
}
