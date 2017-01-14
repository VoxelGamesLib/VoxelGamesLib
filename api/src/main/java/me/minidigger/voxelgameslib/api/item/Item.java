package me.minidigger.voxelgameslib.api.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.ImplementMe;

/**
 * A item a player can have in his inventory
 */
public interface Item<T> extends ImplementMe<T> {
    
    /**
     * @return the material this item is made of
     */
    Material getMaterial();
    
    /**
     * changes the material this item is made of
     *
     * @param material the new material
     */
    void setMaterial(Material material);
    
    /**
     * @return the variation of this item
     */
    // todo handle item variants better
    byte getVariation();
    
    /**
     * changes the variation of this item
     *
     * @param variation the new variation
     */
    void setVariation(byte variation);
    
    /**
     * @return the amount of this item
     */
    int getAmount();
    
    /**
     * changes the amount of this item
     *
     * @param amount the new amount
     */
    void setAmount(int amount);
    
    /**
     * @return the (display) name of this item
     */
    String getName();
    
    /**
     * changes the (display) name of this item
     *
     * @param name the new (display) name
     */
    void setName(String name);
    
    /**
     * @return the list with all lore lines
     */
    List<String> getLore();
    
    /**
     * sets the list with all lore lines
     *
     * @param lore the new list with all lore lines
     */
    void setLore(List<String> lore);
    
    /**
     * adds a new line to the lore of this item
     *
     * @param line the new line
     */
    void addLore(String line);
    
    /**
     * clear the lore for this item
     */
    void clearLore();
    
    /**
     * @return a list with tags, often implemented using nbt
     */
    @Nonnull
    Map<String, Object> getTags();
    
    /**
     * sets the tags for this item, often implemented using nbt
     *
     * @param tags the new tags
     */
    void setTags(@Nonnull Map<String, Object> tags);
    
    /**
     * adds a new tag to this item
     *
     * @param key   the key
     * @param value the value of the tag
     */
    void addTag(String key, Object value);
    
    /**
     * removes a tag from this item
     *
     * @param key the key of the tab to be removed
     */
    void removeTag(String key);
    
    /**
     * clears all (custom) tags for this item
     */
    void clearTags();
}
