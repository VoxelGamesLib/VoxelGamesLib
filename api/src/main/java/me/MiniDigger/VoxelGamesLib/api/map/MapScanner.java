package me.MiniDigger.VoxelGamesLib.api.map;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by Martin on 04.10.2016.
 */
@Singleton
public abstract class MapScanner {


    public void scan(Map map) {

    }

    /**
     * Searches the map for "markers". Most of the time these are implemented as tile entities
     * (skulls)
     *
     * @param map    the map to scan
     * @param center the center location
     * @param range  the range in where to scan
     * @return the list of found markers
     */
    public abstract List<Marker> searchForMarkers(Map map, Vector3D center, int range);
}
