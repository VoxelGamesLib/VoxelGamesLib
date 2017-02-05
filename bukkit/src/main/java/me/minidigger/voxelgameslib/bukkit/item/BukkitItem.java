package me.minidigger.voxelgameslib.bukkit.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.bukkit.converter.MaterialConverter;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Martin on 12.01.2017.
 */
public class BukkitItem implements Item<ItemStack> {

    @Inject
    private MaterialConverter materialConverter;

    private ItemStack itemStack;

    @Nonnull
    @Override
    public ItemStack getImplementationType() {
        return itemStack;
    }

    @Override
    public void setImplementationType(@Nonnull ItemStack is) {
        itemStack = is;
    }

    @Override
    public Material getMaterial() {
        return materialConverter.toVGL(itemStack.getType());
    }

    @Override
    public void setMaterial(Material material) {
        if (itemStack == null) {
            itemStack = new ItemStack(materialConverter.fromVGL(material));
        } else {
            itemStack.setType(materialConverter.fromVGL(material));
        }
    }

    @Override
    public byte getVariation() {
        return itemStack.getData().getData();
    }

    @Override
    public void setVariation(byte variation) {
        itemStack.getData().setData(variation);
    }

    @Override
    public int getAmount() {
        return itemStack.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        itemStack.setAmount(amount);
    }

    @Override
    public String getName() {
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            return itemStack.getItemMeta().getDisplayName();
        }
        return getMaterial().name();
    }

    @Override
    public void setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    @Override
    public List<String> getLore() {
        return itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();
    }

    @Override
    public void setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    @Override
    public void addLore(String line) {
        List<String> lore = getLore();
        lore.add(line);
        setLore(lore);
    }

    @Override
    public void clearLore() {
        setLore(new ArrayList<>());
    }

    @Nonnull
    @Override
    public Map<String, Object> getTags() {
        //TODO reimplement tags
        return null;
    }

    @Override
    public void addTag(String key, Object value) {

    }

    @Override
    public void removeTag(String key) {

    }

    @Override
    public void clearTags() {

    }

    @Override
    public void setTags(@Nonnull Map tags) {

    }
}
