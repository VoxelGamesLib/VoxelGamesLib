package me.MiniDigger.VoxelGamesLib.api.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;

/**
 * Created by Martin on 04.10.2016.
 */
@Data
public class Map {

    private String name;
    private String worldName;
    private List<Marker> markers = new ArrayList<>();
    private List<ChestMarker> chestMarkers = new ArrayList<>();

    public Optional<ChestMarker> getChestMarker(String name) {
        return chestMarkers.stream().filter(chestMarker -> chestMarker.getData().equalsIgnoreCase(name)).findAny();
    }
}
