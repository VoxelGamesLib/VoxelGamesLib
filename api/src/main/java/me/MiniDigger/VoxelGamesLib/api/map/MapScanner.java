package me.MiniDigger.VoxelGamesLib.api.map;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.java.Log;

/**
 * Scans the map for markers.
 */
@Log
public abstract class MapScanner {

    public void scan(Map map, Vector3D center, int range) {
        searchForMarkers(map, center, range);

        List<Marker> errored = new ArrayList<>();

        map.getMarkers().stream().filter(marker -> marker.getData().startsWith("chest:")).forEach(marker -> {
            String name = marker.getData().replace("chest:", "");
            if (map.getChestMarker(name) == null) {
                log.warning("Could not find a chest " + name + " for marker at " + marker.getLoc().toString());
                errored.add(marker);
            }
        });

        map.getMarkers().removeAll(errored);
    }

    /**
     * Searches the map for "markers". Most of the time these are implemented as tile entities
     * (skulls)
     *
     * @param map    the map to scan
     * @param center the center location
     * @param range  the range in where to scan
     */
    public abstract void searchForMarkers(Map map, Vector3D center, int range);
}
