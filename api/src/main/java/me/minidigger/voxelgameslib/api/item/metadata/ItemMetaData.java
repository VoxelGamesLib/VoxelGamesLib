package me.minidigger.voxelgameslib.api.item.metadata;

import me.minidigger.voxelgameslib.api.item.Item;

/**
 * A item meta data is a wrapper for additional data a item might have
 */
public interface ItemMetaData {

    /**
     * @return The item this meta data object provides more info for
     */
    Item getItem();

    /**
     * Sets the underlying item, should only be called by the meta data factory!
     *
     * @param item the new item
     */
    void setItem(Item item);

    /**
     * Updates the underlying item<br>
     * <b>INTERNAL. SHOULD ONLY BE USED BY THE IMPLEMENTATION</b>
     */
    void internal_update();
}
