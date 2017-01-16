package me.minidigger.voxelgameslib.website.model;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Martin on 16.01.2017.
 */
@Data
@AllArgsConstructor
public class Icon {

    @Expose
    private String link;
    @Expose
    private String icon;
}
