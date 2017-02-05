package me.minidigger.voxelgameslib.hub;

import me.minidigger.voxelgameslib.api.feature.features.ClearInventoryFeature;
import me.minidigger.voxelgameslib.api.feature.features.GameModeFeature;
import me.minidigger.voxelgameslib.api.feature.features.HealFeature;
import me.minidigger.voxelgameslib.api.feature.features.MapFeature;
import me.minidigger.voxelgameslib.api.feature.features.NoBlockBreakFeature;
import me.minidigger.voxelgameslib.api.feature.features.NoBlockPlaceFeature;
import me.minidigger.voxelgameslib.api.feature.features.NoDamageFeature;
import me.minidigger.voxelgameslib.api.feature.features.SpawnFeature;
import me.minidigger.voxelgameslib.api.phase.AbstractPhase;
import me.minidigger.voxelgameslib.api.user.GameMode;

/**
 * Created by Martin on 05.02.2017.
 */
public class HubPhase extends AbstractPhase {

    @Override
    public void init() {
        setName("HubPhase");
        super.init();
        setAllowJoin(true);
        setAllowSpectate(false);

        MapFeature mapFeature = getGame().createFeature(MapFeature.class, this);
        mapFeature.setShouldUnload(true);
        addFeature(mapFeature);

        SpawnFeature spawnFeature = getGame().createFeature(SpawnFeature.class, this);
        spawnFeature.setRespawn(true);
        addFeature(spawnFeature);

        GameModeFeature gameModeFeature = getGame().createFeature(GameModeFeature.class, this);
        gameModeFeature.setGameMode(GameMode.SURVIVAL);
        addFeature(gameModeFeature);

        NoDamageFeature noDamageFeature = getGame().createFeature(NoDamageFeature.class, this);
        addFeature(noDamageFeature);

        NoBlockBreakFeature noBlockBreakFeature = getGame().createFeature(NoBlockBreakFeature.class, this);
        addFeature(noBlockBreakFeature);

        NoBlockPlaceFeature noBlockPlaceFeature = getGame().createFeature(NoBlockPlaceFeature.class, this);
        addFeature(noBlockPlaceFeature);

        ClearInventoryFeature clearInventoryFeature = getGame().createFeature(ClearInventoryFeature.class, this);
        addFeature(clearInventoryFeature);

        HealFeature healFeature = getGame().createFeature(HealFeature.class, this);
        addFeature(healFeature);
    }
}
