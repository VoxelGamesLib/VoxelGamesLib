package me.minidigger.voxelgames.survivalgames;

import me.minidigger.voxelgameslib.api.game.AbstractGame;
import me.minidigger.voxelgameslib.api.phase.phases.GracePhase;
import me.minidigger.voxelgameslib.api.phase.phases.LobbyPhase;
import me.minidigger.voxelgameslib.api.phase.phases.VotePhase;

/**
 * Created by Martin on 26.10.2016.
 */
public class SurvivalGamesGame extends AbstractGame {
    
    public SurvivalGamesGame() {
        super(SurvivalGamesModule.GAMEMODE);
    }
    
    @Override
    public void initGame() {
        setMinPlayers(2);
        setMaxPlayers(14);
        
        LobbyPhase lobbyPhase = createPhase(LobbyPhase.class);
        VotePhase votePhase = createPhase(VotePhase.class);
        GracePhase gracePhase = createPhase(GracePhase.class);
        
        lobbyPhase.setNextPhase(votePhase);
        votePhase.setNextPhase(gracePhase);
        
        activePhase = lobbyPhase;
    }
}
