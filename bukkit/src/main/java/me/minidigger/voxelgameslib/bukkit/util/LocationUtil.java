package me.minidigger.voxelgameslib.bukkit.util;

import me.minidigger.voxelgameslib.api.map.Vector3D;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Small util to convert Location <-> Vector3D
 */
public class LocationUtil {
    
    /**
     * Converts a location into a vector
     *
     * @param loc the location to convert
     * @return the converted vector
     */
    public static Vector3D toVector(Location loc) {
        return new Vector3D(loc.getX(), loc.getY(), loc.getZ());
    }
    
    /**
     * Converts a vector (+world) into a location
     *
     * @param world the location the world should have
     * @param loc   the vector to convert
     * @return the converted location
     */
    public static Location toLocation(String world, Vector3D loc) {
        return new Location(Bukkit.getWorld(world), loc.getX(), loc.getY(), loc.getZ());
    }
}
