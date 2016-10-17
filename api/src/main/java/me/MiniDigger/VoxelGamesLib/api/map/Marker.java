package me.MiniDigger.VoxelGamesLib.api.map;

import lombok.Data;

/**
 * Created by Martin on 04.10.2016.
 */
@Data
public class Marker {
    
    private final Vector3D loc;
    private final String data;
    
    public Marker(Vector3D loc, String data) {
        this.loc = loc;
        this.data = data;
    }
}
