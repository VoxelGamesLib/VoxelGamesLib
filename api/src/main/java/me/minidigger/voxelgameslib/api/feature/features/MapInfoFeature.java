package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.api.scoreboard.StringScoreboardLine;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;

/**
 * Displays some information about the current map in the scoreboard of the phase
 */
public class MapInfoFeature extends AbstractFeature {

    @Override
    public void start() {
        MapFeature mapFeature = getPhase().getFeature(MapFeature.class);
        Map map = mapFeature.getMap();

        ScoreboardFeature scoreboardFeature = getPhase().getFeature(ScoreboardFeature.class);
        Scoreboard scoreboard = scoreboardFeature.getScoreboard();

        for (String mode : map.getInfo().getGamemodes()) {
            scoreboard.addLine(new StringScoreboardLine(mode));
        }
        scoreboard.addLine(new StringScoreboardLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Gamemodes: "));
        scoreboard.addLine(new StringScoreboardLine(map.getInfo().getAuthor()));
        scoreboard.addLine(new StringScoreboardLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Author: "));
        scoreboard.addLine(new StringScoreboardLine(map.getInfo().getName()));
        scoreboard.addLine(new StringScoreboardLine(ChatColor.YELLOW + "" + ChatColor.BOLD + "Map: "));
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
