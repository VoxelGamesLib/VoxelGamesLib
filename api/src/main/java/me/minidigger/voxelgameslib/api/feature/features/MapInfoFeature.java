package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.feature.FeatureInfo;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;

@FeatureInfo(name = "MapInfoFeature", author = "MiniDigger", version = "1.0",
        description = "Displays some information about the current map in the scoreboard of the phase")
public class MapInfoFeature extends AbstractFeature {

    @Override
    public void start() {
        MapFeature mapFeature = getPhase().getFeature(MapFeature.class);
        Map map = mapFeature.getMap();

        ScoreboardFeature scoreboardFeature = getPhase().getFeature(ScoreboardFeature.class);
        Scoreboard scoreboard = scoreboardFeature.getScoreboard();

        for (String mode : map.getInfo().getGamemodes()) {
            scoreboard.createAndAddLine(mode);
        }

        scoreboard.createAndAddLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Gamemodes: ");
        scoreboard.createAndAddLine(map.getInfo().getAuthor());
        scoreboard.createAndAddLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Author: ");
        scoreboard.createAndAddLine(map.getInfo().getName());
        scoreboard.createAndAddLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Map: ");
    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDependencies() {
        return new Class[]{MapFeature.class, ScoreboardFeature.class};
    }
}
