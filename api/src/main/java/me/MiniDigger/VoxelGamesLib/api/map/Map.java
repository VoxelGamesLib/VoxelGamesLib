package me.MiniDigger.VoxelGamesLib.api.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import me.MiniDigger.VoxelGamesLib.api.lang.Lang;
import me.MiniDigger.VoxelGamesLib.api.lang.LangKey;
import me.MiniDigger.VoxelGamesLib.api.user.User;

/**
 * Created by Martin on 04.10.2016.
 */
@Data
public class Map {

    private String author;
    private List<String> gameModes;
    private Vector3D center;
    private int radius;
    private String displayname;
    private String worldName;
    private List<Marker> markers = new ArrayList<>();
    private List<ChestMarker> chestMarkers = new ArrayList<>();
    private boolean loaded;

    public Map(String displayName, String worldName, String author, List<String> gameModes, Vector3D center, int radius) {
        this.displayname = displayName;
        this.worldName = worldName;
        this.author = author;
        this.gameModes = gameModes;
        this.center = center;
        this.radius = radius;
    }

    public Optional<ChestMarker> getChestMarker(String name) {
        return chestMarkers.stream().filter(chestMarker -> chestMarker.getData().equalsIgnoreCase(name)).findAny();
    }

    public void printSummery(User sender) {
        Lang.msg(sender, LangKey.WORLD_CREATOR_MAP_SUMMERY, displayname, worldName, author, center, radius, gameModes);
        //TODO print summery of map
    }
}
