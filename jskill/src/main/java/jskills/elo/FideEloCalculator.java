package jskills.elo;

import jskills.GameRatingInfo;

/**
 * Including ELO's scheme as a simple comparison. See
 * http://en.wikipedia.org/wiki/Elo_rating_system#Theory for more details
 */
public class FideEloCalculator extends TwoPlayerEloCalculator {

    public FideEloCalculator() {
        this(new FideKFactor());
    }

    public FideEloCalculator(FideKFactor kFactor) {
        super(kFactor);
    }

    @Override
    protected double getPlayerWinProbability(GameRatingInfo gameInfo, double playerRating, double opponentRating) {
        double ratingDifference = opponentRating - playerRating;

        return 1.0 /
                (1.0 + Math.pow(10.0, ratingDifference / (2 * gameInfo.getBeta())));
    }
}