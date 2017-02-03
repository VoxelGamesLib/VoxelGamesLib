package jskills;

import com.google.gson.annotations.Expose;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * Parameters about the game for calculating the TrueSkill.
 */
@Embeddable
public class GameRatingInfo {

    private static final double defaultInitialMean = 25.0;
    private static final double defaultBeta = defaultInitialMean / 6.0;
    private static final double defaultDrawProbability = 0.10;
    private static final double defaultDynamicsFactor = defaultInitialMean / 300.0;
    private static final double defaultInitialStandardDeviation = defaultInitialMean / 3.0;

    @Expose
    @Basic
    private double initialMean;
    @Expose
    @Basic
    private double initialStandardDeviation;
    @Expose
    @Basic
    private double beta;
    @Expose
    @Basic
    private double dynamicsFactor;
    @Expose
    @Basic
    private double drawProbability;

    public GameRatingInfo(double initialMean, double initialStandardDeviation,
                          double beta, double dynamicFactor, double drawProbability) {
        this.initialMean = initialMean;
        this.initialStandardDeviation = initialStandardDeviation;
        this.beta = beta;
        this.dynamicsFactor = dynamicFactor;
        this.drawProbability = drawProbability;
    }

    protected GameRatingInfo() {
        //JPA
    }

    public static GameRatingInfo getDefaultGameInfo() {
        // We return a fresh copy since we have public setters that can mutate state
        return new GameRatingInfo(defaultInitialMean,
                defaultInitialStandardDeviation,
                defaultBeta,
                defaultDynamicsFactor,
                defaultDrawProbability);
    }

    public Rating getDefaultRating() {
        return new Rating(initialMean, initialStandardDeviation);
    }

    public double getInitialMean() {
        return initialMean;
    }

    public double getInitialStandardDeviation() {
        return initialStandardDeviation;
    }

    public double getBeta() {
        return beta;
    }

    public double getDynamicsFactor() {
        return dynamicsFactor;
    }

    public double getDrawProbability() {
        return drawProbability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameRatingInfo gameInfo = (GameRatingInfo) o;

        if (Double.compare(gameInfo.getInitialMean(), getInitialMean()) != 0) return false;
        if (Double.compare(gameInfo.getInitialStandardDeviation(), getInitialStandardDeviation()) != 0)
            return false;
        if (Double.compare(gameInfo.getBeta(), getBeta()) != 0) return false;
        if (Double.compare(gameInfo.getDynamicsFactor(), getDynamicsFactor()) != 0) return false;
        return Double.compare(gameInfo.getDrawProbability(), getDrawProbability()) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getInitialMean());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getInitialStandardDeviation());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getBeta());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getDynamicsFactor());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getDrawProbability());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "initialMean=" + initialMean +
                ", initialStandardDeviation=" + initialStandardDeviation +
                ", beta=" + beta +
                ", dynamicsFactor=" + dynamicsFactor +
                ", drawProbability=" + drawProbability +
                '}';
    }
}