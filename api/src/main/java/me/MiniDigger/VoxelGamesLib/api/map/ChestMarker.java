package me.MiniDigger.VoxelGamesLib.api.map;

import me.MiniDigger.VoxelGamesLib.api.item.Item;

import javax.annotation.Nonnull;

/**
 * Created by Martin on 04.10.2016.
 */
public class ChestMarker extends Marker {
    
    private final Item[] items;
    
    public ChestMarker(@Nonnull Vector3D loc, @Nonnull String name, @Nonnull Item[] items) {
        super(loc, name);
        this.items = items;
    }
    
    @Nonnull
    public Item[] getItems() {
        return items;
    }
}
