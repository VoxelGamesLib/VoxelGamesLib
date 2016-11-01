package me.minidigger.voxelgameslib.api.phase.phases;

import me.minidigger.voxelgameslib.api.GameConstants;
import me.minidigger.voxelgameslib.api.feature.features.VoteFeature;
import me.minidigger.voxelgameslib.api.phase.TimedPhase;

/**
 * Can be used to vote on the next gamemode or the next map.
 */
public class VotePhase extends TimedPhase {
    
    @Override
    public void init() {
        setName("VotePhase");
        setAllowJoin(true);
        setAllowSpectate(false);
        setTicks(60 * GameConstants.TPS);
        
        System.out.println("add vote feature");
        addFeature(getGame().createFeature(VoteFeature.class, this));
    }
    
    //TODO vote phase
}
