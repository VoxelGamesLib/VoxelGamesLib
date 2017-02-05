package me.minidigger.voxelgameslib.bukkit.converter;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.block.Direction;
import me.minidigger.voxelgameslib.api.convert.Converter;

import org.bukkit.block.BlockFace;

/**
 * Created by Martin on 04.02.2017.
 */
@Singleton
public class DirectionConverter implements Converter<Direction, BlockFace> {
    @Override
    public BlockFace fromVGL(Direction direction) {
        return BlockFace.valueOf(direction.name());
    }

    @Override
    public Direction toVGL(BlockFace blockFace) {
        return Direction.valueOf(blockFace.name());
    }
}
