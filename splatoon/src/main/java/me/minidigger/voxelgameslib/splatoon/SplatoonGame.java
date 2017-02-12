package me.minidigger.voxelgameslib.splatoon;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.game.AbstractGame;
import me.minidigger.voxelgameslib.api.game.GameDefinition;
import me.minidigger.voxelgameslib.api.game.GameInfo;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.phase.phases.GracePhase;
import me.minidigger.voxelgameslib.api.phase.phases.LobbyPhase;
import me.minidigger.voxelgameslib.api.phase.phases.VotePhase;
import me.minidigger.voxelgameslib.api.world.WorldHandler;

/**
 * Created by Martin on 05.02.2017.
 */
@GameInfo(name = "Splatoon", author = "MiniDigger", version = "v1.0", description = "Splatton Description")
public class SplatoonGame extends AbstractGame {

    @Inject
    private WorldHandler worldHandler;

    public SplatoonGame() {
        super(SplatoonModule.GAMEMODE);
    }

    @Override
    public void initGameFromModule() {
        setMinPlayers(2);
        setMaxPlayers(14);

        LobbyPhase lobbyPhase = createPhase(LobbyPhase.class);
        VotePhase votePhase = createPhase(VotePhase.class);
        GracePhase gracePhase = createPhase(GracePhase.class);
        SplatoonPhase splatoonPhase = createPhase(SplatoonPhase.class);

        lobbyPhase.setNextPhase(votePhase);
        votePhase.setNextPhase(gracePhase);
        gracePhase.setNextPhase(splatoonPhase);

        activePhase = lobbyPhase;

        loadMap();
    }

    @Override
    public void initGameFromDefinition(@Nonnull GameDefinition gameDefinition) {
        super.initGameFromDefinition(gameDefinition);

        loadMap();
    }

    private void loadMap() {
        //TODO move this somewhere else (unify)
        // TODO this doesn't respect if a user changed the lobby in the config
        Optional<MapInfo> info = worldHandler.getMapInfo("Lobby");
        if (info.isPresent()) {
            putGameData("lobbymap", info.get());
        } else {
            abortGame();
        }
    }
}
