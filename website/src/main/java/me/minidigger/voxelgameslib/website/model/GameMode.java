package me.minidigger.voxelgameslib.website.model;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Martin on 16.01.2017.
 */
@Data
@AllArgsConstructor
public class GameMode {

    @Expose
    private List<Icon> icons;
    @Expose
    private String image;
    @Expose
    private String name;
    @Expose
    private String sub;
    @Expose
    private String description;
    @Expose
    private String slug;
}
