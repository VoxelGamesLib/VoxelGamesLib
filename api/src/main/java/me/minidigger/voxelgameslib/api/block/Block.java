package me.minidigger.voxelgameslib.api.block;

import me.minidigger.voxelgameslib.api.ImplementMe;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.map.Vector3D;

/**
 * Represents a single block at a location in the world
 */
public interface Block<T> extends ImplementMe<T> {
    
    /**
     * @return the material this block is made of
     */
    Material getMaterial();
    
    /**
     * changes the material this block is made of
     *
     * @param material the new material
     */
    void setMaterial(Material material);
    
    /**
     * @return the variant of the block
     */
    //TODO handle block variants better
    byte getVariant();
    
    /**
     * changes the variant of this block
     *
     * @param variant the new variant
     */
    void setVariant(byte variant);
    
    /**
     * @return the location of this block
     */
    Vector3D getLocation();
    
    /**
     * @return the world this block is placed in
     */
    String getWorld();
}