package me.minidigger.voxelgameslib.api.elo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.minidigger.voxelgameslib.api.feature.features.DuelFeature;
import me.minidigger.voxelgameslib.api.feature.features.TeamFeature;
import me.minidigger.voxelgameslib.api.game.Game;
import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.team.Team;
import me.minidigger.voxelgameslib.api.user.User;

import jskills.IPlayer;
import jskills.ITeam;
import jskills.Rating;
import jskills.SkillCalculator;
import jskills.trueskill.FactorGraphTrueSkillCalculator;
import lombok.extern.java.Log;

/**
 * Created by Martin on 28.01.2017.
 */
@Log
public class EloHandler implements Handler {

    private SkillCalculator calculator = new FactorGraphTrueSkillCalculator();

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void handleGameEnd(Game game, TeamFeature teamFeature, Team winner) {
    }

    public void handleGameEnd(Game game, DuelFeature duelFeature, User winner) {
        List<ITeam> teams = new ArrayList<>();
        teams.add(new jskills.Team(duelFeature.getOne(), duelFeature.getOne().getData().getRating(game.getGameMode())));
        teams.add(new jskills.Team(duelFeature.getTwo(), duelFeature.getTwo().getData().getRating(game.getGameMode())));
        if (!winner.equals(duelFeature.getOne())) {
            Collections.reverse(teams);
        }
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(game.getGameMode().getRatingInfo(), teams, 1, 2);
        for (IPlayer iPlayer : newRatings.keySet()) {
            if (!(iPlayer instanceof User)) {
                log.warning("WTF");
                continue;
            }

            User user = (User) iPlayer;
            user.getData().saveRating(game.getGameMode(), newRatings.get(iPlayer));
            log.info("New Rating for " + user.getData().getDisplayName() + " is "
                    + newRatings.get(iPlayer).getMean() + "(" + newRatings.get(iPlayer).getStandardDeviation() + ")");
        }
    }

    public void handleGameEnd(Game game, User winner) {

    }
}
