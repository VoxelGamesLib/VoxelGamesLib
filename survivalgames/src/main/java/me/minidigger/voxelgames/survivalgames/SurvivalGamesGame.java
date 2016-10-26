package me.minidigger.voxelgames.survivalgames;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.game.AbstractGame;
import me.minidigger.voxelgameslib.api.phase.Phase;

/**
 * Created by Martin on 26.10.2016.
 */
public class SurvivalGamesGame extends AbstractGame {
    
    public SurvivalGamesGame(@Nonnull Phase firstPhase) {
        super(SurvivalGamesModule.GAMEMODE, firstPhase);
    }
}
