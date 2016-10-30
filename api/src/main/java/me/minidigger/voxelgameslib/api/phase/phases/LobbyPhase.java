package me.minidigger.voxelgameslib.api.phase.phases;

import me.minidigger.voxelgameslib.api.phase.AbstractPhase;

/**
 * The lobby phase is the phase that part before the game starts. players can do cool activities
 * while they wait for more players to join.
 */
public class LobbyPhase extends AbstractPhase {
    
    //TODO lobby phase
    
    @Override
    public void init() {
        setName("LobbyPhase");
        setAllowJoin(true);
        setAllowSpectate(false);
    }
}
