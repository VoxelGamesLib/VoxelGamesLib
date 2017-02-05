package me.minidigger.voxelgameslib.bukkit.block.metadata;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.block.metadata.SignMetaData;

import org.bukkit.block.Sign;

/**
 * Created by Martin on 05.02.2017.
 */
public class BukkitSignMetaData implements SignMetaData {

    private Block block;
    private Sign sign;

    @Override
    public String[] getLines() {
        return sign.getLines();
    }

    @Override
    public String getLine(int i) {
        return sign.getLine(i);
    }

    @Override
    public void setLine(String line, int i) {
        sign.setLine(i, line);
        sign.update();
    }

    @Override
    public void setLines(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            sign.setLine(i, lines[i]);
        }
        sign.update();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void setBlock(Block block) {
        this.block = block;
        this.sign = (Sign) ((org.bukkit.block.Block) block.getImplementationType()).getState();
    }
}
