package me.minidigger.voxelgameslib.bukkit.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.Material;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import lombok.extern.java.Log;

/**
 * Created by Martin on 05.10.2016.
 */
@Log
public class BukkitItem extends Item {
    
    public BukkitItem(Material material, byte variation, int amount, String name, List<String> lore, Map<String, Object> tags) {
        super(material, variation, amount, name, lore, tags);
    }
    
    public BukkitItem() {
        // injector constructor
        super();
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
        
        // special tag handeling //TODO do we have a better way for this?
        Map<String, Object> tags = new HashMap<>();
        // skull owner
        if (is.getType() == org.bukkit.Material.SKULL) {
            if (is.hasItemMeta() && is.getItemMeta() instanceof SkullMeta) {
                SkullMeta meta = (SkullMeta) is.getItemMeta();
                tags.put("SkullOwner", meta.getOwner());
            }
        }
        
        return new BukkitItem(Material.fromId(is.getTypeId()), is.getData().getData(), is.getAmount(), name, lore, tags);
    }
    
    public ItemStack toItemStack() {
        ItemStack is = new ItemStack(BukkitMaterial.toMaterial(getMaterial()));
        is.setAmount(getAmount());
        is.setDurability(getVariation());
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(getName());
        meta.setLore(getLore());
        is.setItemMeta(meta);
        
        // special tag handeling //TODO do we have a better way for this?
        // skull owner
        if (getTags().containsKey("SkullOwner")) {
            if (is.getType() == org.bukkit.Material.SKULL_ITEM && is.hasItemMeta() && is.getItemMeta() instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) is.getItemMeta();
                skullMeta.setOwner((String) getTags().get("SkullOwner"));
                is.setItemMeta(skullMeta);
            } else {
                log.warning("Failed to translate item " + this + " to itemstack " + is);
            }
        }
        
        return is;
    }
}
