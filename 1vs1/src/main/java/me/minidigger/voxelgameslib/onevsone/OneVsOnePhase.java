package me.minidigger.voxelgameslib.onevsone;

import me.minidigger.voxelgameslib.api.GameConstants;
import me.minidigger.voxelgameslib.api.feature.features.DuelFeature;
import me.minidigger.voxelgameslib.api.feature.features.GameModeFeature;
import me.minidigger.voxelgameslib.api.feature.features.MapFeature;
import me.minidigger.voxelgameslib.api.feature.features.SpawnFeature;
import me.minidigger.voxelgameslib.api.phase.TimedPhase;
import me.minidigger.voxelgameslib.api.user.GameMode;

/**
 * Created by Martin on 28.01.2017.
 */
public class OneVsOnePhase extends TimedPhase {

    @Override
    public void init() {
        setName("OneVsOnePhase");
        setTicks(2 * 60 * GameConstants.TPS);
        super.init();
        setAllowJoin(false);
        setAllowSpectate(true);

        MapFeature mapFeature = getGame().createFeature(MapFeature.class, this);
        mapFeature.setShouldUnload(true);
        addFeature(mapFeature);

        SpawnFeature spawnFeature = getGame().createFeature(SpawnFeature.class, this);
        spawnFeature.setRespawn(false);
        addFeature(spawnFeature);

        GameModeFeature gameModeFeature = getGame().createFeature(GameModeFeature.class, this);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);
        addFeature(gameModeFeature);

        OneVsOneFeature oneVsOneFeature = getGame().createFeature(OneVsOneFeature.class, this);
        addFeature(oneVsOneFeature);

        DuelFeature duelFeature = getGame().createFeature(DuelFeature.class, this);
        addFeature(duelFeature);
    }
}
