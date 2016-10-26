package me.minidigger.voxelgameslib.api.item;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Call to create items, as a chainable builder.
 */
public class ItemBuilder {
    
    private Item item;
    
    /**
     * Starts creating a new item
     *
     * @param material a base material to go off from
     * @param injector the injector that should be used to create a new instance of the item class
     */
    public ItemBuilder(Material material, Injector injector) {
        item = injector.getInstance(Item.class);
        item.setMaterial(material);
    }
    
    /**
     * Modifies an existing item
     *
     * @param item the exisiting item to modify
     */
    public ItemBuilder(Item item) {
        this.item = item;
    }
    
    /**
     * sets the material
     *
     * @param material the new material
     * @return this builder for chaining
     */
    public ItemBuilder material(Material material) {
        item.setMaterial(material);
        return this;
    }
    
    /**
     * Sets the variation (for some server mods, data value) for this item.
     *
     * @param variation the new variation to set
     * @return this builder for chaining
     */
    public ItemBuilder variation(byte variation) {
        item.setVariation(variation);
        return this;
    }
    
    /**
     * sets the amount
     *
     * @param amount the new amount to set
     * @return this builder for chaining
     */
    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }
    
    /**
     * sets the (display) name
     *
     * @param name the name to set
     * @return this builder for chaining
     */
    public ItemBuilder name(String name) {
        item.setName(name);
        return this;
    }
    
    /**
     * adds a new line to the lore, creates the lore if there was no line added yet
     *
     * @param lore the new line to add
     * @return this builder for chaining
     */
    public ItemBuilder addLore(String lore) {
        List<String> loreList = item.getLore();
        if (loreList == null) {
            loreList = new ArrayList<>();
        }
        loreList.add(lore);
        item.setLore(loreList);
        return this;
    }
    
    /**
     * Adds multiple lines to the lore, creates the lore if there was no line added yet
     *
     * @param lore the new lines to add
     * @return this builder for chaining
     */
    public ItemBuilder addLore(String... lore) {
        List<String> loreList = item.getLore();
        if (loreList == null) {
            loreList = new ArrayList<>();
        }
        Collections.addAll(loreList, lore);
        item.setLore(loreList);
        return this;
    }
    
    /**
     * Clears the lore of this item
     *
     * @return this builder for chaining
     */
    public ItemBuilder clearLore() {
        item.setLore(new ArrayList<>());
        return this;
    }
    
    /**
     * Addeds a tag to this item. a tag is a NBT tag for most server mods.
     *
     * @param tag   the key of the tag to add
     * @param value the value for the tag
     * @return this builder for chaining
     */
    public ItemBuilder tag(String tag, Object value) {
        Map<String, Object> tags = item.getTags();
        if (tags == null) {
            tags = new HashMap<>();
        }
        tags.put(tag, value);
        item.setTags(tags);
        return this;
    }
    
    /**
     * Clears all tags for this item
     *
     * @return this builder for chaining
     */
    public ItemBuilder clearTags() {
        item.setTags(new HashMap<>());
        return this;
    }
    
    /**
     * @return the constructed item
     */
    public Item build() {
        return item;
    }
}
