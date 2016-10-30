package me.minidigger.voxelgameslib.api.phase.phases;

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
    }
    
    //TODO vote phase
}
