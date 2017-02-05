package me.minidigger.voxelgameslib.hub;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.game.AbstractGame;
import me.minidigger.voxelgameslib.api.game.GameDefinition;
import me.minidigger.voxelgameslib.api.game.GameInfo;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.world.WorldHandler;

/**
 * Created by Martin on 05.02.2017.
 */
@GameInfo(name = "Hub", author = "MiniDigger", version = "v1.0", description = "Hub Description")
public class HubGame extends AbstractGame {

    @Inject
    private WorldHandler worldHandler;

    public HubGame() {
        super(HubModule.GAMEMODE);
    }

    @Override
    public void initGameFromModule() {
        setMinPlayers(-1);
        setMaxPlayers(-1);

        activePhase = createPhase(HubPhase.class);

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
        Optional<MapInfo> info = worldHandler.getMapInfo("hub");
        if (info.isPresent()) {
            putGameData("map", info.get());
        } else {
            abortGame();
        }
    }
}
