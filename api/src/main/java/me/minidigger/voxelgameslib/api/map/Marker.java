package me.minidigger.voxelgameslib.api.map;

import javax.annotation.Nonnull;

import lombok.Data;

/**
 * A marker marks a special position in the world. they are most of the time implemented by tile
 * entities so that the scanning process is easier/faster.
 */
@Data
public class Marker {
    
    @Nonnull
    private final Vector3D loc;
    private final double yaw;
    @Nonnull
    private final String data;
    
    /**
     * Constructs a new marker
     *
     * @param loc  the location that this marker marks
     * @param yaw  the yaw for this marker
     * @param data the data of this marker
     */
    public Marker(@Nonnull Vector3D loc, double yaw, @Nonnull String data) {
        this.loc = loc;
        this.yaw = yaw;
        this.data = data;
    }
}
