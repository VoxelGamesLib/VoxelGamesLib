package me.MiniDigger.VoxelGamesLib.api.map;

import lombok.Data;

/**
 * Created by Martin on 04.10.2016.
 */
@Data
public class Marker {

    private Vector3D loc;
    private String data;

    public Marker(Vector3D loc, String data) {
        this.loc = loc;
        this.data = data;
    }
}
