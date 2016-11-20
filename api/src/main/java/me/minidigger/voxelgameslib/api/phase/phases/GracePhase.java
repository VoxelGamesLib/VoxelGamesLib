package me.minidigger.voxelgameslib.api.phase.phases;

import me.minidigger.voxelgameslib.api.GameConstants;
import me.minidigger.voxelgameslib.api.feature.features.MapFeature;
import me.minidigger.voxelgameslib.api.feature.features.SpawnFeature;
import me.minidigger.voxelgameslib.api.phase.TimedPhase;

/**
 * The grace phase is the phase before the real action starts. pvp is disabled, players are expected
 * to run away, hide or collect stuff.
 */
public class GracePhase extends TimedPhase {
    
    @Override
    public void init() {
        setName("GracePhase");
        super.init();
        setAllowJoin(false);
        setAllowSpectate(true);
        setTicks(60 * GameConstants.TPS);
        
        MapFeature mapFeature = getGame().createFeature(MapFeature.class, this);
        mapFeature.setShouldUnload(false);
        addFeature(mapFeature);
    
        SpawnFeature spawnFeature = getGame().createFeature(SpawnFeature.class,this);
        addFeature(spawnFeature);
    }
    
    //TODO GracePhase
}
