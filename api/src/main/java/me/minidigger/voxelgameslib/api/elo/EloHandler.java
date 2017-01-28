package me.minidigger.voxelgameslib.api.elo;

import me.minidigger.voxelgameslib.api.feature.features.DuelFeature;
import me.minidigger.voxelgameslib.api.feature.features.TeamFeature;
import me.minidigger.voxelgameslib.api.game.Game;
import me.minidigger.voxelgameslib.api.handler.Handler;

import jskills.SkillCalculator;

/**
 * Created by Martin on 28.01.2017.
 */
public class EloHandler implements Handler {

    private SkillCalculator skillCalculator;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void handleGameEnd(Game game, TeamFeature teamFeature) {
    }

    public void handleGameEnd(Game game, DuelFeature duelFeature) {
    }

    public void handleGameEnd(Game game) {
    }
}
