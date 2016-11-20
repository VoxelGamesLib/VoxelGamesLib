package me.minidigger.voxelgameslib.api.phase.phases;

import me.minidigger.voxelgameslib.api.GameConstants;
import me.minidigger.voxelgameslib.api.feature.features.MapFeature;
import me.minidigger.voxelgameslib.api.feature.features.SpawnFeature;
import me.minidigger.voxelgameslib.api.feature.features.VoteFeature;
import me.minidigger.voxelgameslib.api.phase.TimedPhase;

/**
 * Can be used to vote on the next gamemode or the next map.
 */
public class VotePhase extends TimedPhase {
    
    @Override
    public void init() {
        setName("VotePhase");
        super.init();
        setAllowJoin(true);
        setAllowSpectate(false);
        setTicks(60 * GameConstants.TPS);
        
        addFeature(getGame().createFeature(VoteFeature.class, this));
        
        MapFeature mapFeature = getGame().createFeature(MapFeature.class, this);
        mapFeature.setShouldUnload(true);
        mapFeature.setMapGameDataKey("lobbymap");
        addFeature(mapFeature);
        
        SpawnFeature spawnFeature = getGame().createFeature(SpawnFeature.class, this);
        spawnFeature.setInitialSpawn(false);
        addFeature(spawnFeature);
    }
    
    //TODO vote phase
}
