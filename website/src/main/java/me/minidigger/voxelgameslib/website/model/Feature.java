package me.minidigger.voxelgameslib.website.model;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Martin on 19.01.2017.
 */
@Data
@AllArgsConstructor
public class Feature {

    @Expose
    private String name;
    @Expose
    private String fullName;
    @Expose
    private String author;
    @Expose
    private String version;
    @Expose
    private String description;
    @Expose
    private List<String> dependencies;
    @Expose
    private List<String> params;
    @Expose
    private String slug;
}
