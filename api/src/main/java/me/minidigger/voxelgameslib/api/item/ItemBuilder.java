package me.minidigger.voxelgameslib.api.item;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin on 23.10.2016.
 */
public class ItemBuilder {
    
    private Item item;
    
    private Material material;
    private byte variation;
    private int amount;
    private String name;
    private List<String> lore;
    
    public ItemBuilder(Material material, Injector injector) {
        item = injector.getInstance(Item.class);
        item.setMaterial(material);
    }
    
    public ItemBuilder(Item item) {
        this.item = item;
    }
    
    public ItemBuilder material(Material material) {
        item.setMaterial(material);
        return this;
    }
    
    public ItemBuilder variation(byte variation) {
        item.setVariation(variation);
        return this;
    }
    
    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }
    
    public ItemBuilder name(String name) {
        item.setName(name);
        return this;
    }
    
    public ItemBuilder addLore(String lore) {
        List<String> loreList = item.getLore();
        if (loreList == null) {
            loreList = new ArrayList<>();
        }
        loreList.add(lore);
        item.setLore(loreList);
        return this;
    }
    
    public ItemBuilder addLore(String... lore) {
        List<String> loreList = item.getLore();
        if (loreList == null) {
            loreList = new ArrayList<>();
        }
        Collections.addAll(loreList, lore);
        item.setLore(loreList);
        return this;
    }
    
    public ItemBuilder clearLore() {
        item.setLore(new ArrayList<>());
        return this;
    }
    
    public ItemBuilder tag(String tag, Object value) {
        Map<String, Object> tags = item.getTags();
        if (tags == null) {
            tags = new HashMap<>();
        }
        tags.put(tag, value);
        item.setTags(tags);
        return this;
    }
    
    public ItemBuilder clearTags() {
        item.setTags(new HashMap<>());
        return this;
    }
    
    public Item build() {
        return item;
    }
}
