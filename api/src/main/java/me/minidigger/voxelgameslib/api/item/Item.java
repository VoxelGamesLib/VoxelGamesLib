package me.minidigger.voxelgameslib.api.item;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Abstract item. Subclasses may provide conversation methods
 */
@Data
@AllArgsConstructor
public class Item {
    
    @Expose
    private Material material;
    @Expose
    private byte variation;
    @Expose
    private int amount;
    @Expose
    private String name;
    @Expose
    private List<String> lore;
    @Nonnull
    @Expose
    private Map<String, Object> tags = new HashMap<>();
    
    /**
     * injection constructor. creates a item with air and amount 1
     */
    public Item() {
        // injector constructor
        material = Material.AIR;
        variation = 0;
        amount = 1;
    }
}
