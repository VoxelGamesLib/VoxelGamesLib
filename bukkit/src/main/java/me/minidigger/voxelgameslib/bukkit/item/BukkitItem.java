package me.minidigger.voxelgameslib.bukkit.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.MiniDigger.VoxelGamesLib.api.item.Item;
import me.MiniDigger.VoxelGamesLib.api.item.Material;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by Martin on 05.10.2016.
 */
public class BukkitItem extends Item {
    
    public BukkitItem(Material material, byte variation, int amount, String name, List<String> lore) {
        super(material, variation, amount, name, lore);
    }
    
    public static BukkitItem fromItemStack(@Nonnull ItemStack is) {
        String name = is.getType().name();
        List<String> lore = new ArrayList<>();
        if (is.hasItemMeta()) {
            ItemMeta meta = is.getItemMeta();
            if (meta.hasDisplayName()) {
                name = meta.getDisplayName();
            }
            if (meta.hasLore()) {
                lore = meta.getLore();
            }
        }
        return new BukkitItem(Material.fromId(is.getTypeId()), is.getData().getData(), is.getAmount(), name, lore);
    }
    
    public ItemStack toItemStack() {
        ItemStack is = new ItemStack(BukkitMaterial.toMaterial(getMaterial()));
        is.setAmount(getAmount());
        is.setData(new MaterialData(is.getType(), getVariation()));
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(getName());
        meta.setLore(getLore());
        is.setItemMeta(meta);
        return is;
    }
}
