package me.minidigger.voxelgameslib.api.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.user.User;

import lombok.Data;

/**
 * A map. A map is a world that is playable in gamemodes. has all kind of extra informations about a
 * world.
 */
@Data
public class Map {
    
    private String author;
    private List<String> gameModes;
    private Vector3D center;
    private int radius;
    private String displayname;
    private String worldName;
    @Nonnull
    private List<Marker> markers = new ArrayList<>();
    @Nonnull
    private List<ChestMarker> chestMarkers = new ArrayList<>();
    private boolean loaded;
    
    public Map(@Nonnull String displayName, @Nonnull String worldName, @Nonnull String author, @Nonnull List<String> gameModes, @Nonnull Vector3D center, int radius) {
        this.displayname = displayName;
        this.worldName = worldName;
        this.author = author;
        this.gameModes = gameModes;
        this.center = center;
        this.radius = radius;
    }
    
    //TODO javadoc for Map
    @Nonnull
    public Optional<ChestMarker> getChestMarker(@Nonnull String name) {
        return chestMarkers.stream().filter(chestMarker -> chestMarker.getData().equalsIgnoreCase(name)).findAny();
    }
    
    public void printSummery(@Nonnull User sender) {
        Lang.msg(sender, LangKey.WORLD_CREATOR_MAP_SUMMERY, displayname, worldName, author, center, radius, gameModes);
        //TODO print summery of map
    }
}
