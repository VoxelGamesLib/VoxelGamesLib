package me.minidigger.voxelgameslib.api.item.metadata;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.item.Item;

/**
 * Factory which is capable of creating new block meta data instances
 */
@Singleton
public class ItemMetaFactory {

    @Inject
    private Injector injector;

    /**
     * Create a new item meta data instance for a specified item.
     * if no suiting instance is found, it will return an empty one.
     *
     * @param item the item to get the meta data for
     * @return the new meta data instance
     */
    public ItemMetaData createMetaData(Item item) {
        switch (item.getMaterial()) {
            case SKULL_ITEM:
                return instance(SkullItemMetaData.class, item);
            default:
                return instance(EmptyItemMetaData.class, item);
        }
    }

    private ItemMetaData instance(Class<? extends ItemMetaData> clazz, Item item) {
        ItemMetaData metaData = injector.getInstance(clazz);
        metaData.setItem(item);
        return metaData;
    }
}
