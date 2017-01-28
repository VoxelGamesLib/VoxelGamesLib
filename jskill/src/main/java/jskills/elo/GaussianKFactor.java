package jskills.elo;

import jskills.GameRatingInfo;

public class GaussianKFactor extends KFactor {
    // From paper
    static final double StableDynamicsKFactor = 24.0;

    public GaussianKFactor() {
        super(StableDynamicsKFactor);
    }

    public GaussianKFactor(GameRatingInfo gameInfo, double latestGameWeightingFactor) {
        super(latestGameWeightingFactor * gameInfo.getBeta()
                * Math.sqrt(Math.PI));
    }
}