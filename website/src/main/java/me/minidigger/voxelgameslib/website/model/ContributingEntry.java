package me.minidigger.voxelgameslib.website.model;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Martin on 26.01.2017.
 */
@Data
@AllArgsConstructor
public class ContributingEntry {

    @Expose
    private String icon;
    @Expose
    private String title;
    @Expose
    private String content;
    @Expose
    private boolean dev;
}
