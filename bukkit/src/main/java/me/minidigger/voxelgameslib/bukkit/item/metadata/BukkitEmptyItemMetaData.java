package me.minidigger.voxelgameslib.bukkit.item.metadata;

import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.metadata.EmptyItemMetaData;

/**
 * Created by Martin on 09.02.2017.
 */
public class BukkitEmptyItemMetaData implements EmptyItemMetaData {

    private Item item;

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void update() {
        // ignore
    }
}
