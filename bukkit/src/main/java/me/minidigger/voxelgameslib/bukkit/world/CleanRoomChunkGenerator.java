package me.minidigger.voxelgameslib.bukkit.world;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * Created by Martin on 07.10.2016.
 */
public class CleanRoomChunkGenerator extends ChunkGenerator {
    
    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return createChunkData(world);
    }
    
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        return new byte[32768];
    }
    
    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return new byte[world.getMaxHeight() / 16][];
    }
    
    @Override
    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return new short[world.getMaxHeight() / 16][];
    }
}
