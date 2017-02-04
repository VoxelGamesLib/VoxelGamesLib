package me.minidigger.voxelgameslib.bukkit.block;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.convert.Converter;

/**
 * Created by Martin on 04.02.2017.
 */
@Singleton
public class BlockConverter implements Converter<Block, org.bukkit.block.Block> {

    @Inject
    private Injector injector;

    @Override
    public org.bukkit.block.Block fromVGL(Block block) {
        return (org.bukkit.block.Block) block.getImplementationType();
    }

    @Override
    public Block toVGL(org.bukkit.block.Block block) {
        Block b = injector.getInstance(Block.class);
        //noinspection unchecked
        b.setImplementationType(block);
        return b;
    }
}
