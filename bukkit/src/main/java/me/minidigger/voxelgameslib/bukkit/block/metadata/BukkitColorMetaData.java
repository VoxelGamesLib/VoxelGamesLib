package me.minidigger.voxelgameslib.bukkit.block.metadata;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.block.metadata.ColorMetaData;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;

/**
 * Created by Martin on 05.02.2017.
 */
public class BukkitColorMetaData implements ColorMetaData {

    private Block<org.bukkit.block.Block> block;

    @Override
    public ChatColor getColor() {
        return ChatColor.values()[block.getImplementationType().getData()];
    }

    @Override
    public void setColor(ChatColor color) {
        block.getImplementationType().setData((byte) color.ordinal());
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void setBlock(Block block) {
        //noinspection unchecked
        this.block = block;
    }

    @Override
    public void internal_update() {
        // ignored
    }
}
