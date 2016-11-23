package me.minidigger.voxelgames.survivalgames;

import java.util.Optional;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.game.AbstractGame;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.phase.phases.GracePhase;
import me.minidigger.voxelgameslib.api.phase.phases.LobbyPhase;
import me.minidigger.voxelgameslib.api.phase.phases.VotePhase;
import me.minidigger.voxelgameslib.api.world.WorldHandler;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Martin on 26.10.2016.
 */
public class SurvivalGamesGame extends AbstractGame {
    
    @Inject
    private WorldHandler worldHandler;
    
    public SurvivalGamesGame() {
        super(SurvivalGamesModule.GAMEMODE);
    }
    
    @Override
    public void initGameFromModule() {
        setMinPlayers(2);
        setMaxPlayers(14);
        
        LobbyPhase lobbyPhase = createPhase(LobbyPhase.class);
        VotePhase votePhase = createPhase(VotePhase.class);
        GracePhase gracePhase = createPhase(GracePhase.class);
        
        lobbyPhase.setNextPhase(votePhase);
        votePhase.setNextPhase(gracePhase);
    
        activePhase = lobbyPhase;
    
        Optional<MapInfo> info = worldHandler.getMapInfo("Lobby");
        if (info.isPresent()) {
            putGameData("lobbymap", info.get());
        } else {
            // TODO abort game
            broadcastMessage(new TextComponent("YOU ARE ALL DOOMED"));
            endGame();
        }
    }
}
