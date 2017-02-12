package me.minidigger.voxelgameslib.api.item;

import java.util.List;

import me.minidigger.voxelgameslib.api.ImplementMe;
import me.minidigger.voxelgameslib.api.item.metadata.ItemMetaData;

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
     * @return the item meta data for this item
     */
    ItemMetaData getItemMetaData();

    /**
     * @return the damage of this item
     */
    short getDamage();

    /**
     * changes the damage value of this item
     *
     * @param damage the new damage
     */
    void setDamage(short damage);

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
}
