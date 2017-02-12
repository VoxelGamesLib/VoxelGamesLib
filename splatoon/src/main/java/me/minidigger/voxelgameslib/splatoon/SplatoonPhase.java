package me.minidigger.voxelgameslib.splatoon;

import me.minidigger.voxelgameslib.api.GameConstants;
import me.minidigger.voxelgameslib.api.feature.features.GameModeFeature;
import me.minidigger.voxelgameslib.api.feature.features.MapFeature;
import me.minidigger.voxelgameslib.api.feature.features.NoBlockBreakFeature;
import me.minidigger.voxelgameslib.api.feature.features.NoBlockPlaceFeature;
import me.minidigger.voxelgameslib.api.feature.features.ScoreboardFeature;
import me.minidigger.voxelgameslib.api.feature.features.SpawnFeature;
import me.minidigger.voxelgameslib.api.feature.features.TeamFeature;
import me.minidigger.voxelgameslib.api.phase.TimedPhase;
import me.minidigger.voxelgameslib.api.user.GameMode;

/**
 * Created by Martin on 05.02.2017.
 */
public class SplatoonPhase extends TimedPhase {

    @Override
    public void init() {
        setName("SplatoonPhase");
        setTicks(3 * 60 * GameConstants.TPS);
        super.init();
        setAllowJoin(true);
        setAllowSpectate(false);

        MapFeature mapFeature = getGame().createFeature(MapFeature.class, this);
        mapFeature.setShouldUnload(true);
        addFeature(mapFeature);

        SpawnFeature spawnFeature = getGame().createFeature(SpawnFeature.class, this);
        addFeature(spawnFeature);

        NoBlockBreakFeature noBlockBreakFeature = getGame().createFeature(NoBlockBreakFeature.class, this);
        //    addFeature(noBlockBreakFeature); //TODO REENABLE

        NoBlockPlaceFeature noBlockPlaceFeature = getGame().createFeature(NoBlockPlaceFeature.class, this);
        //   addFeature(noBlockPlaceFeature);

        GameModeFeature gameModeFeature = getGame().createFeature(GameModeFeature.class, this);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);
        addFeature(gameModeFeature);

        ScoreboardFeature scoreboardFeature = getGame().createFeature(ScoreboardFeature.class, this);
        addFeature(scoreboardFeature);

        TeamFeature teamFeature = getGame().createFeature(TeamFeature.class, this);
        addFeature(teamFeature);

        SplatoonFeature splatoonFeature = getGame().createFeature(SplatoonFeature.class, this);
        addFeature(splatoonFeature);
    }
}
