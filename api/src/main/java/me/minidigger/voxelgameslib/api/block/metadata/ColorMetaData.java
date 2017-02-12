package me.minidigger.voxelgameslib.api.block.metadata;

import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;

/**
 * Metadata wrapper for colorable blocks
 */
public interface ColorMetaData extends BlockMetaData {

    /**
     * @return returns the color of this block
     */
    ChatColor getColor();

    /**
     * Changes the color of this block
     *
     * @param color the new color
     */
    void setColor(ChatColor color);
}
