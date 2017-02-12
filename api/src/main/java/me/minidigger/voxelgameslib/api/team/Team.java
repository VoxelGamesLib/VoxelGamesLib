package me.minidigger.voxelgameslib.api.team;

import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;

import lombok.Data;

/**
 * Created by Martin on 28.01.2017.
 */
@Data
public class Team extends jskills.Team {

    private String name = "<unknown>";
    private ChatColor color = ChatColor.GREEN;

}
