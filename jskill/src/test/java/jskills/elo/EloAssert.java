package jskills.elo;

import java.util.Collection;
import java.util.Map;

import jskills.GameRatingInfo;
import jskills.IPlayer;
import jskills.ITeam;
import jskills.PairwiseComparison;
import jskills.Player;
import jskills.Rating;
import jskills.Team;

import static org.junit.Assert.assertEquals;

public class EloAssert {
    private static final double ErrorTolerance = 0.1;

    public static void assertChessRating(TwoPlayerEloCalculator calculator,
                                         double player1BeforeRating,
                                         double player2BeforeRating,
                                         PairwiseComparison player1Result,
                                         double player1AfterRating,
                                         double player2AfterRating) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);

        Collection<ITeam> teams = Team.concat(
                new Team(player1, new EloRating(player1BeforeRating)),
                new Team(player2, new EloRating(player2BeforeRating)));

        GameRatingInfo chessGameInfo = new GameRatingInfo(1200, 0, 200, 0, 0);

        Map<IPlayer, Rating> result = calculator.calculateNewRatings(chessGameInfo, teams,
                player1Result.equals(PairwiseComparison.WIN) ? new int[]{1, 2} :
                        player1Result.equals(PairwiseComparison.LOSE) ? new int[]{2, 1} :
            /* Draw */ new int[]{1, 1});


        assertEquals(player1AfterRating, result.get(player1).getMean(), ErrorTolerance);
        assertEquals(player2AfterRating, result.get(player2).getMean(), ErrorTolerance);
    }
}