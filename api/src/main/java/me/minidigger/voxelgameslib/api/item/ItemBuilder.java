package me.minidigger.voxelgameslib.api.item;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.item.metadata.ItemMetaData;

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
    public ItemBuilder(@Nonnull Material material, @Nonnull Injector injector) {
        item = injector.getInstance(Item.class);
        item.setMaterial(material);
    }

    /**
     * Modifies an existing item
     *
     * @param item the exisiting item to modify
     */
    public ItemBuilder(@Nonnull Item item) {
        this.item = item;
    }

    /**
     * sets the material
     *
     * @param material the new material
     * @return this builder for chaining
     */
    @Nonnull
    public ItemBuilder material(@Nonnull Material material) {
        item.setMaterial(material);
        return this;
    }

    /**
     * Sets the variation (for some server mods, data value) for this item.
     *
     * @param variation the new variation to set
     * @return this builder for chaining
     */
    @Nonnull
    public ItemBuilder variation(byte variation) {
        item.setDamage(variation);
        return this;
    }

    /**
     * sets the amount
     *
     * @param amount the new amount to set
     * @return this builder for chaining
     */
    @Nonnull
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
    @Nonnull
    public ItemBuilder name(@Nonnull String name) {
        item.setName(name);
        return this;
    }

    /**
     * adds a new line to the lore, creates the lore if there was no line added yet
     *
     * @param lore the new line to add
     * @return this builder for chaining
     */
    @Nonnull
    public ItemBuilder addLore(@Nonnull String lore) {
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
    @Nonnull
    public ItemBuilder addLore(@Nonnull String... lore) {
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
    @Nonnull
    public ItemBuilder clearLore() {
        item.setLore(new ArrayList<>());
        return this;
    }

    /**
     * @return the constructed item
     */
    @Nonnull
    public Item build() {
        return item;
    }

    /**
     * Allows modification of the item meta
     *
     * @param consumer consumer to modify the meta
     * @return this builder for chaining
     */
    public ItemBuilder meta(Consumer<ItemMetaData> consumer) {
        ItemMetaData meta = item.getItemMetaData();
        consumer.accept(meta);
        return this;
    }
}
