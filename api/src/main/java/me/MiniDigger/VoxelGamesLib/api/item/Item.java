package me.MiniDigger.VoxelGamesLib.api.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Abstract item. Subclasses may provide conversation methods
 */
@Data
@AllArgsConstructor
public class Item {

    private Material material;
    private byte variation;
    private int amount;
    private String name;
    private List<String> lore;
    private Map<String, Object> tags = new HashMap<>();

    public Item() {
        // injector constructor
        material = Material.AIR;
        variation = 0;
        amount = 1;
    }
}
