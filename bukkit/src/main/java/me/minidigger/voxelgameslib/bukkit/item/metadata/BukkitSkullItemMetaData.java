package me.minidigger.voxelgameslib.bukkit.item.metadata;

import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.metadata.SkullItemMetaData;
import me.minidigger.voxelgameslib.bukkit.item.BukkitItem;

import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by Martin on 09.02.2017.
 */
public class BukkitSkullItemMetaData implements SkullItemMetaData {

    private Item item;
    private SkullMeta skullMeta;

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
        this.skullMeta = (SkullMeta) ((BukkitItem) item).getImplementationType().getItemMeta();
    }

    @Override
    public boolean hasOwner() {
        return skullMeta.hasOwner();
    }

    @Override
    public void setOwner(String name) {
        skullMeta.setOwner(name);
    }

    @Override
    public String getOwner() {
        return skullMeta.getOwner();
    }

    @Override
    public SkullType getType() {
        return SkullType.valueOf(item.getDamage());
    }

    @Override
    public void setType(SkullType type) {
        item.setDamage((byte) type.getId());
    }

    @Override
    public void update() {
        ((BukkitItem) item).getImplementationType().setItemMeta(skullMeta);
    }
}
