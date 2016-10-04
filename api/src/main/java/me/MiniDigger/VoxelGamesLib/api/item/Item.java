package me.MiniDigger.VoxelGamesLib.api.item;

import java.util.List;

import lombok.Data;

/**
 * Abstract item. Subclasses may provide conversation methods
 */
@Data
public class Item {

    private Material material;
    private byte variation;
    private int amount;
    private String name;
    private List<String> lore;

    //TODO item builder
    //TODO coversation method
}
