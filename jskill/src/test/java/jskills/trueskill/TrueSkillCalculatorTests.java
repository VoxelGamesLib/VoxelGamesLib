package jskills.trueskill;

import java.util.Collection;
import java.util.Map;

import jskills.GameRatingInfo;
import jskills.IPlayer;
import jskills.ITeam;
import jskills.Player;
import jskills.Rating;
import jskills.SkillCalculator;
import jskills.Team;

import static org.junit.Assert.assertEquals;

public class TrueSkillCalculatorTests {

    private final static double ErrorTolerance = 0.085;

    // These are the roll-up ones

    public static void TestAllTwoPlayerScenarios(SkillCalculator calculator) {
        TwoPlayerTestNotDrawn(calculator);
        TwoPlayerTestDrawn(calculator);
        OneOnOneMassiveUpsetDrawTest(calculator);

        TwoPlayerChessTestNotDrawn(calculator);
    }

    public static void TestAllTwoTeamScenarios(SkillCalculator calculator) {
        OneOnTwoSimpleTest(calculator);
        OneOnTwoDrawTest(calculator);
        OneOnTwoSomewhatBalanced(calculator);
        OneOnThreeDrawTest(calculator);
        OneOnThreeSimpleTest(calculator);
        OneOnSevenSimpleTest(calculator);

        TwoOnTwoSimpleTest(calculator);
        TwoOnTwoUnbalancedDrawTest(calculator);
        TwoOnTwoDrawTest(calculator);
        TwoOnTwoUpsetTest(calculator);

        ThreeOnTwoTests(calculator);

        FourOnFourSimpleTest(calculator);
    }

    public static void TestAllMultipleTeamScenarios(SkillCalculator calculator) {
        ThreeTeamsOfOneNotDrawn(calculator);
        ThreeTeamsOfOneDrawn(calculator);
        FourTeamsOfOneNotDrawn(calculator);
        FiveTeamsOfOneNotDrawn(calculator);
        EightTeamsOfOneDrawn(calculator);
        EightTeamsOfOneUpset(calculator);
        SixteenTeamsOfOneNotDrawn(calculator);

        TwoOnFourOnTwoWinDraw(calculator);
    }

    public static void TestPartialPlayScenarios(SkillCalculator calculator) {
        OneOnTwoBalancedPartialPlay(calculator);
    }

//------------------- Actual Tests ---------------------------
// If you see more than 3 digits of precision in the decimal point, then the 
// expected values calculated from F# RalfH's implementation with the same 
// input. It didn't support teams, so team values all came from the online 
// calculator at 
// http://atom.research.microsoft.com/trueskill/rankcalculator.aspx
//
// All match quality expected values came from the online calculator

// In both cases, there may be some discrepancy after the first decimal point. 
// I think this is due to my implementation using slightly higher precision in 
// GaussianDistribution.

//------------------------------------------------------------------------------
// Two Player Tests
//------------------------------------------------------------------------------

    private static void TwoPlayerTestNotDrawn(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Collection<ITeam> teams = Team.concat(team1, team2);

        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(29.39583201999924, 7.171475587326186, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(20.60416798000076, 7.171475587326186, player2NewRating);

        assertMatchQuality(0.447, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void TwoPlayerTestDrawn(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Collection<ITeam> teams = Team.concat(team1, team2);

        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 1);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(25.0, 6.4575196623173081, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(25.0, 6.4575196623173081, player2NewRating);

        assertMatchQuality(0.447, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void TwoPlayerChessTestNotDrawn(SkillCalculator calculator) {
        // Inspired by a real bug :-)
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        GameRatingInfo gameInfo = new GameRatingInfo(1200.0, 1200.0 / 3.0, 200.0, 1200.0 / 300.0, 0.03);

        Team team1 = new Team(player1, new Rating(1301.0007, 42.9232));
        Team team2 = new Team(player2, new Rating(1188.7560, 42.5570));

        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, Team.concat(team1, team2), 1, 2);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(1304.7820836053318, 42.843513887848658, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(1185.0383099003536, 42.485604606897752, player2NewRating);
    }

    private static void OneOnOneMassiveUpsetDrawTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team().addPlayer(player1, gameInfo.getDefaultRating());

        Player<Integer> player2 = new Player<Integer>(2);

        Team team2 = new Team().addPlayer(player2, new Rating(50, 12.5));

        Collection<ITeam> teams = Team.concat(team1, team2);

        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 1);

        // Winners
        assertRating(31.662, 7.137, newRatingsWinLose.get(player1));

        // Losers
        assertRating(35.010, 7.910, newRatingsWinLose.get(player2));

        assertMatchQuality(0.110, calculator.calculateMatchQuality(gameInfo, teams));
    }

    //------------------------------------------------------------------------------
    // Two Team Tests
    //------------------------------------------------------------------------------

    private static void TwoOnTwoSimpleTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating())
                .addPlayer(player2, gameInfo.getDefaultRating());

        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        Team team2 = new Team()
                .addPlayer(player3, gameInfo.getDefaultRating())
                .addPlayer(player4, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(28.108, 7.774, newRatingsWinLose.get(player1));
        assertRating(28.108, 7.774, newRatingsWinLose.get(player2));

        // Losers
        assertRating(21.892, 7.774, newRatingsWinLose.get(player3));
        assertRating(21.892, 7.774, newRatingsWinLose.get(player4));

        assertMatchQuality(0.447, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void TwoOnTwoDrawTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating())
                .addPlayer(player2, gameInfo.getDefaultRating());

        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        Team team2 = new Team()
                .addPlayer(player3, gameInfo.getDefaultRating())
                .addPlayer(player4, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 1);

        // Winners
        assertRating(25, 7.455, newRatingsWinLose.get(player1));
        assertRating(25, 7.455, newRatingsWinLose.get(player2));

        // Losers
        assertRating(25, 7.455, newRatingsWinLose.get(player3));
        assertRating(25, 7.455, newRatingsWinLose.get(player4));

        assertMatchQuality(0.447, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void TwoOnTwoUnbalancedDrawTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, new Rating(15, 8))
                .addPlayer(player2, new Rating(20, 6));

        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        Team team2 = new Team()
                .addPlayer(player3, new Rating(25, 4))
                .addPlayer(player4, new Rating(30, 3));

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 1);

        // Winners
        assertRating(21.570, 6.556, newRatingsWinLose.get(player1));
        assertRating(23.696, 5.418, newRatingsWinLose.get(player2));

        // Losers
        assertRating(23.357, 3.833, newRatingsWinLose.get(player3));
        assertRating(29.075, 2.931, newRatingsWinLose.get(player4));

        assertMatchQuality(0.214, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void TwoOnTwoUpsetTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, new Rating(20, 8))
                .addPlayer(player2, new Rating(25, 6));

        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        Team team2 = new Team()
                .addPlayer(player3, new Rating(35, 7))
                .addPlayer(player4, new Rating(40, 5));

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(29.698, 7.008, newRatingsWinLose.get(player1));
        assertRating(30.455, 5.594, newRatingsWinLose.get(player2));

        // Losers
        assertRating(27.575, 6.346, newRatingsWinLose.get(player3));
        assertRating(36.211, 4.768, newRatingsWinLose.get(player4));

        assertMatchQuality(0.084, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void FourOnFourSimpleTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating())
                .addPlayer(player2, gameInfo.getDefaultRating())
                .addPlayer(player3, gameInfo.getDefaultRating())
                .addPlayer(player4, gameInfo.getDefaultRating());

        Player<Integer> player5 = new Player<Integer>(5);
        Player<Integer> player6 = new Player<Integer>(6);
        Player<Integer> player7 = new Player<Integer>(7);
        Player<Integer> player8 = new Player<Integer>(8);

        Team team2 = new Team()
                .addPlayer(player5, gameInfo.getDefaultRating())
                .addPlayer(player6, gameInfo.getDefaultRating())
                .addPlayer(player7, gameInfo.getDefaultRating())
                .addPlayer(player8, gameInfo.getDefaultRating());


        Collection<ITeam> teams = Team.concat(team1, team2);

        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(27.198, 8.059, newRatingsWinLose.get(player1));
        assertRating(27.198, 8.059, newRatingsWinLose.get(player2));
        assertRating(27.198, 8.059, newRatingsWinLose.get(player3));
        assertRating(27.198, 8.059, newRatingsWinLose.get(player4));

        // Losers
        assertRating(22.802, 8.059, newRatingsWinLose.get(player5));
        assertRating(22.802, 8.059, newRatingsWinLose.get(player6));
        assertRating(22.802, 8.059, newRatingsWinLose.get(player7));
        assertRating(22.802, 8.059, newRatingsWinLose.get(player8));

        assertMatchQuality(0.447, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void OneOnTwoSimpleTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating());

        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);

        Team team2 = new Team()
                .addPlayer(player2, gameInfo.getDefaultRating())
                .addPlayer(player3, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(33.730, 7.317, newRatingsWinLose.get(player1));

        // Losers
        assertRating(16.270, 7.317, newRatingsWinLose.get(player2));
        assertRating(16.270, 7.317, newRatingsWinLose.get(player3));

        assertMatchQuality(0.135, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void OneOnTwoSomewhatBalanced(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, new Rating(40, 6));

        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);

        Team team2 = new Team()
                .addPlayer(player2, new Rating(20, 7))
                .addPlayer(player3, new Rating(25, 8));

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(42.744, 5.602, newRatingsWinLose.get(player1));

        // Losers
        assertRating(16.266, 6.359, newRatingsWinLose.get(player2));
        assertRating(20.123, 7.028, newRatingsWinLose.get(player3));

        assertMatchQuality(0.478, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void OneOnThreeSimpleTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating());

        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        Team team2 = new Team()
                .addPlayer(player2, gameInfo.getDefaultRating())
                .addPlayer(player3, gameInfo.getDefaultRating())
                .addPlayer(player4, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(36.337, 7.527, newRatingsWinLose.get(player1));

        // Losers
        assertRating(13.663, 7.527, newRatingsWinLose.get(player2));
        assertRating(13.663, 7.527, newRatingsWinLose.get(player3));
        assertRating(13.663, 7.527, newRatingsWinLose.get(player4));

        assertMatchQuality(0.012, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void OneOnTwoDrawTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating());

        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);

        Team team2 = new Team()
                .addPlayer(player2, gameInfo.getDefaultRating())
                .addPlayer(player3, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 1);

        // Winners
        assertRating(31.660, 7.138, newRatingsWinLose.get(player1));

        // Losers
        assertRating(18.340, 7.138, newRatingsWinLose.get(player2));
        assertRating(18.340, 7.138, newRatingsWinLose.get(player3));

        assertMatchQuality(0.135, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void OneOnThreeDrawTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating());

        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);

        Team team2 = new Team()
                .addPlayer(player2, gameInfo.getDefaultRating())
                .addPlayer(player3, gameInfo.getDefaultRating())
                .addPlayer(player4, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 1);

        // Winners
        assertRating(34.990, 7.455, newRatingsWinLose.get(player1));

        // Losers
        assertRating(15.010, 7.455, newRatingsWinLose.get(player2));
        assertRating(15.010, 7.455, newRatingsWinLose.get(player3));
        assertRating(15.010, 7.455, newRatingsWinLose.get(player4));

        assertMatchQuality(0.012, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void OneOnSevenSimpleTest(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, gameInfo.getDefaultRating());

        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);
        Player<Integer> player6 = new Player<Integer>(6);
        Player<Integer> player7 = new Player<Integer>(7);
        Player<Integer> player8 = new Player<Integer>(8);

        Team team2 = new Team()
                .addPlayer(player2, gameInfo.getDefaultRating())
                .addPlayer(player3, gameInfo.getDefaultRating())
                .addPlayer(player4, gameInfo.getDefaultRating())
                .addPlayer(player5, gameInfo.getDefaultRating())
                .addPlayer(player6, gameInfo.getDefaultRating())
                .addPlayer(player7, gameInfo.getDefaultRating())
                .addPlayer(player8, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(40.582, 7.917, newRatingsWinLose.get(player1));

        // Losers
        assertRating(9.418, 7.917, newRatingsWinLose.get(player2));
        assertRating(9.418, 7.917, newRatingsWinLose.get(player3));
        assertRating(9.418, 7.917, newRatingsWinLose.get(player4));
        assertRating(9.418, 7.917, newRatingsWinLose.get(player5));
        assertRating(9.418, 7.917, newRatingsWinLose.get(player6));
        assertRating(9.418, 7.917, newRatingsWinLose.get(player7));
        assertRating(9.418, 7.917, newRatingsWinLose.get(player8));

        assertMatchQuality(0.000, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void ThreeOnTwoTests(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);

        Team team1 = new Team()
                .addPlayer(player1, new Rating(28, 7))
                .addPlayer(player2, new Rating(27, 6))
                .addPlayer(player3, new Rating(26, 5));


        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);

        Team team2 = new Team()
                .addPlayer(player4, new Rating(30, 4))
                .addPlayer(player5, new Rating(31, 3));

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatingsWinLoseExpected = calculator.calculateNewRatings(gameInfo, teams, 1, 2);

        // Winners
        assertRating(28.658, 6.770, newRatingsWinLoseExpected.get(player1));
        assertRating(27.484, 5.856, newRatingsWinLoseExpected.get(player2));
        assertRating(26.336, 4.917, newRatingsWinLoseExpected.get(player3));

        // Losers
        assertRating(29.785, 3.958, newRatingsWinLoseExpected.get(player4));
        assertRating(30.879, 2.983, newRatingsWinLoseExpected.get(player5));

        Map<IPlayer, Rating> newRatingsWinLoseUpset = calculator.calculateNewRatings(gameInfo, Team.concat(team1, team2), 2, 1);

        // Winners
        assertRating(32.012, 3.877, newRatingsWinLoseUpset.get(player4));
        assertRating(32.132, 2.949, newRatingsWinLoseUpset.get(player5));

        // Losers
        assertRating(21.840, 6.314, newRatingsWinLoseUpset.get(player1));
        assertRating(22.474, 5.575, newRatingsWinLoseUpset.get(player2));
        assertRating(22.857, 4.757, newRatingsWinLoseUpset.get(player3));

        assertMatchQuality(0.254, calculator.calculateMatchQuality(gameInfo, teams));
    }

// ------------------------------------------------------------------------------
// Multiple Teams Tests
//------------------------------------------------------------------------------

    private static void TwoOnFourOnTwoWinDraw(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);

        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team()
                .addPlayer(player1, new Rating(40, 4))
                .addPlayer(player2, new Rating(45, 3));

        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);
        Player<Integer> player6 = new Player<Integer>(6);

        Team team2 = new Team()
                .addPlayer(player3, new Rating(20, 7))
                .addPlayer(player4, new Rating(19, 6))
                .addPlayer(player5, new Rating(30, 9))
                .addPlayer(player6, new Rating(10, 4));

        Player<Integer> player7 = new Player<Integer>(7);
        Player<Integer> player8 = new Player<Integer>(8);

        Team team3 = new Team()
                .addPlayer(player7, new Rating(50, 5))
                .addPlayer(player8, new Rating(30, 2));

        Collection<ITeam> teams = Team.concat(team1, team2, team3);
        Map<IPlayer, Rating> newRatingsWinLose = calculator.calculateNewRatings(gameInfo, teams, 1, 2, 2);

        // Winners
        assertRating(40.877, 3.840, newRatingsWinLose.get(player1));
        assertRating(45.493, 2.934, newRatingsWinLose.get(player2));
        assertRating(19.609, 6.396, newRatingsWinLose.get(player3));
        assertRating(18.712, 5.625, newRatingsWinLose.get(player4));
        assertRating(29.353, 7.673, newRatingsWinLose.get(player5));
        assertRating(9.872, 3.891, newRatingsWinLose.get(player6));
        assertRating(48.830, 4.590, newRatingsWinLose.get(player7));
        assertRating(29.813, 1.976, newRatingsWinLose.get(player8));

        assertMatchQuality(0.367, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void ThreeTeamsOfOneNotDrawn(SkillCalculator calculator) {

        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Team team3 = new Team(player3, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2, team3);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 2, 3);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(31.675352419172107, 6.6559853776206905, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(25.000000000003912, 6.2078966412243233, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(18.324647580823971, 6.6559853776218318, player3NewRating);

        assertMatchQuality(0.200, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void ThreeTeamsOfOneDrawn(SkillCalculator calculator) {

        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Team team3 = new Team(player3, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2, team3);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 1, 1);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(25.000, 5.698, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(25.000, 5.695, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(25.000, 5.698, player3NewRating);

        assertMatchQuality(0.200, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void FourTeamsOfOneNotDrawn(SkillCalculator calculator) {

        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Team team3 = new Team(player3, gameInfo.getDefaultRating());
        Team team4 = new Team(player4, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2, team3, team4);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 2, 3, 4);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(33.206680965631264, 6.3481091698077057, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(27.401454693843323, 5.7871629348447584, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(22.598545306188374, 5.7871629348413451, player3NewRating);

        Rating player4NewRating = newRatings.get(player4);
        assertRating(16.793319034361271, 6.3481091698144967, player4NewRating);

        assertMatchQuality(0.089, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void FiveTeamsOfOneNotDrawn(SkillCalculator calculator) {
        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Team team3 = new Team(player3, gameInfo.getDefaultRating());
        Team team4 = new Team(player4, gameInfo.getDefaultRating());
        Team team5 = new Team(player5, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2, team3, team4, team5);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 2, 3, 4, 5);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(34.363135705841188, 6.1361528798112692, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(29.058448805636779, 5.5358352402833413, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(25.000000000031758, 5.4200805474429847, player3NewRating);

        Rating player4NewRating = newRatings.get(player4);
        assertRating(20.941551194426314, 5.5358352402709672, player4NewRating);

        Rating player5NewRating = newRatings.get(player5);
        assertRating(15.636864294158848, 6.136152879829349, player5NewRating);

        assertMatchQuality(0.040, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void EightTeamsOfOneDrawn(SkillCalculator calculator) {

        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);
        Player<Integer> player6 = new Player<Integer>(6);
        Player<Integer> player7 = new Player<Integer>(7);
        Player<Integer> player8 = new Player<Integer>(8);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Team team3 = new Team(player3, gameInfo.getDefaultRating());
        Team team4 = new Team(player4, gameInfo.getDefaultRating());
        Team team5 = new Team(player5, gameInfo.getDefaultRating());
        Team team6 = new Team(player6, gameInfo.getDefaultRating());
        Team team7 = new Team(player7, gameInfo.getDefaultRating());
        Team team8 = new Team(player8, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2, team3, team4, team5, team6, team7, team8);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 1, 1, 1, 1, 1, 1, 1);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(25.000, 4.592, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(25.000, 4.583, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(25.000, 4.576, player3NewRating);

        Rating player4NewRating = newRatings.get(player4);
        assertRating(25.000, 4.573, player4NewRating);

        Rating player5NewRating = newRatings.get(player5);
        assertRating(25.000, 4.573, player5NewRating);

        Rating player6NewRating = newRatings.get(player6);
        assertRating(25.000, 4.576, player6NewRating);

        Rating player7NewRating = newRatings.get(player7);
        assertRating(25.000, 4.583, player7NewRating);

        Rating player8NewRating = newRatings.get(player8);
        assertRating(25.000, 4.592, player8NewRating);

        assertMatchQuality(0.004, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void EightTeamsOfOneUpset(SkillCalculator calculator) {

        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);
        Player<Integer> player6 = new Player<Integer>(6);
        Player<Integer> player7 = new Player<Integer>(7);
        Player<Integer> player8 = new Player<Integer>(8);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, new Rating(10, 8));
        Team team2 = new Team(player2, new Rating(15, 7));
        Team team3 = new Team(player3, new Rating(20, 6));
        Team team4 = new Team(player4, new Rating(25, 5));
        Team team5 = new Team(player5, new Rating(30, 4));
        Team team6 = new Team(player6, new Rating(35, 3));
        Team team7 = new Team(player7, new Rating(40, 2));
        Team team8 = new Team(player8, new Rating(45, 1));

        Collection<ITeam> teams = Team.concat(team1, team2, team3, team4, team5, team6, team7, team8);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 2, 3, 4, 5, 6, 7, 8);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(35.135, 4.506, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(32.585, 4.037, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(31.329, 3.756, player3NewRating);

        Rating player4NewRating = newRatings.get(player4);
        assertRating(30.984, 3.453, player4NewRating);

        Rating player5NewRating = newRatings.get(player5);
        assertRating(31.751, 3.064, player5NewRating);

        Rating player6NewRating = newRatings.get(player6);
        assertRating(34.051, 2.541, player6NewRating);

        Rating player7NewRating = newRatings.get(player7);
        assertRating(38.263, 1.849, player7NewRating);

        Rating player8NewRating = newRatings.get(player8);
        assertRating(44.118, 0.983, player8NewRating);

        assertMatchQuality(0.000, calculator.calculateMatchQuality(gameInfo, teams));
    }

    private static void SixteenTeamsOfOneNotDrawn(SkillCalculator calculator) {

        Player<Integer> player1 = new Player<Integer>(1);
        Player<Integer> player2 = new Player<Integer>(2);
        Player<Integer> player3 = new Player<Integer>(3);
        Player<Integer> player4 = new Player<Integer>(4);
        Player<Integer> player5 = new Player<Integer>(5);
        Player<Integer> player6 = new Player<Integer>(6);
        Player<Integer> player7 = new Player<Integer>(7);
        Player<Integer> player8 = new Player<Integer>(8);
        Player<Integer> player9 = new Player<Integer>(9);
        Player<Integer> player10 = new Player<Integer>(10);
        Player<Integer> player11 = new Player<Integer>(11);
        Player<Integer> player12 = new Player<Integer>(12);
        Player<Integer> player13 = new Player<Integer>(13);
        Player<Integer> player14 = new Player<Integer>(14);
        Player<Integer> player15 = new Player<Integer>(15);
        Player<Integer> player16 = new Player<Integer>(16);
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Team team1 = new Team(player1, gameInfo.getDefaultRating());
        Team team2 = new Team(player2, gameInfo.getDefaultRating());
        Team team3 = new Team(player3, gameInfo.getDefaultRating());
        Team team4 = new Team(player4, gameInfo.getDefaultRating());
        Team team5 = new Team(player5, gameInfo.getDefaultRating());
        Team team6 = new Team(player6, gameInfo.getDefaultRating());
        Team team7 = new Team(player7, gameInfo.getDefaultRating());
        Team team8 = new Team(player8, gameInfo.getDefaultRating());
        Team team9 = new Team(player9, gameInfo.getDefaultRating());
        Team team10 = new Team(player10, gameInfo.getDefaultRating());
        Team team11 = new Team(player11, gameInfo.getDefaultRating());
        Team team12 = new Team(player12, gameInfo.getDefaultRating());
        Team team13 = new Team(player13, gameInfo.getDefaultRating());
        Team team14 = new Team(player14, gameInfo.getDefaultRating());
        Team team15 = new Team(player15, gameInfo.getDefaultRating());
        Team team16 = new Team(player16, gameInfo.getDefaultRating());

        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(
                gameInfo,
                Team.concat(
                        team1, team2, team3, team4, team5,
                        team6, team7, team8, team9, team10,
                        team11, team12, team13, team14, team15,
                        team16),
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

        Rating player1NewRating = newRatings.get(player1);
        assertRating(40.53945776946920, 5.27581643889050, player1NewRating);

        Rating player2NewRating = newRatings.get(player2);
        assertRating(36.80951229454210, 4.71121217610266, player2NewRating);

        Rating player3NewRating = newRatings.get(player3);
        assertRating(34.34726355544460, 4.52440328139991, player3NewRating);

        Rating player4NewRating = newRatings.get(player4);
        assertRating(32.33614722608720, 4.43258628279632, player4NewRating);

        Rating player5NewRating = newRatings.get(player5);
        assertRating(30.55048814671730, 4.38010805034365, player5NewRating);

        Rating player6NewRating = newRatings.get(player6);
        assertRating(28.89277312234790, 4.34859291776483, player6NewRating);

        Rating player7NewRating = newRatings.get(player7);
        assertRating(27.30952161972210, 4.33037679041216, player7NewRating);

        Rating player8NewRating = newRatings.get(player8);
        assertRating(25.76571046519540, 4.32197078088701, player8NewRating);

        Rating player9NewRating = newRatings.get(player9);
        assertRating(24.23428953480470, 4.32197078088703, player9NewRating);

        Rating player10NewRating = newRatings.get(player10);
        assertRating(22.69047838027800, 4.33037679041219, player10NewRating);

        Rating player11NewRating = newRatings.get(player11);
        assertRating(21.10722687765220, 4.34859291776488, player11NewRating);

        Rating player12NewRating = newRatings.get(player12);
        assertRating(19.44951185328290, 4.38010805034375, player12NewRating);

        Rating player13NewRating = newRatings.get(player13);
        assertRating(17.66385277391300, 4.43258628279643, player13NewRating);

        Rating player14NewRating = newRatings.get(player14);
        assertRating(15.65273644455550, 4.52440328139996, player14NewRating);

        Rating player15NewRating = newRatings.get(player15);
        assertRating(13.19048770545810, 4.71121217610273, player15NewRating);

        Rating player16NewRating = newRatings.get(player16);
        assertRating(9.46054223053080, 5.27581643889032, player16NewRating);
    }

//------------------------------------------------------------------------------
// Partial Play Tests
//------------------------------------------------------------------------------

    private static void OneOnTwoBalancedPartialPlay(SkillCalculator calculator) {
        GameRatingInfo gameInfo = GameRatingInfo.getDefaultGameInfo();

        Player<Integer> p1 = new Player<Integer>(1);
        Team team1 = new Team(p1, gameInfo.getDefaultRating());

        Player<Integer> p2 = new Player<Integer>(2, 0.0);
        Player<Integer> p3 = new Player<Integer>(3, 1.00);

        Team team2 = new Team()
                .addPlayer(p2, gameInfo.getDefaultRating())
                .addPlayer(p3, gameInfo.getDefaultRating());

        Collection<ITeam> teams = Team.concat(team1, team2);
        Map<IPlayer, Rating> newRatings = calculator.calculateNewRatings(gameInfo, teams, 1, 2);
        double matchQuality = calculator.calculateMatchQuality(gameInfo, teams);
        // TODO assert something
    }

//------------------------------------------------------------------------------
// Helpers
//------------------------------------------------------------------------------

    private static void assertRating(double expectedMean, double expectedStandardDeviation, Rating actual) {
        assertEquals(actual.getMean(), expectedMean, ErrorTolerance);
        assertEquals(actual.getStandardDeviation(), expectedStandardDeviation, ErrorTolerance);
    }

    private static void assertMatchQuality(double expectedMatchQuality, double actualMatchQuality) {
        assertEquals(actualMatchQuality, expectedMatchQuality, 0.0005);
    }
}