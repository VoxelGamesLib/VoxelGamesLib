package jskills.numerics;

import org.junit.Test;

import static java.lang.Math.sqrt;
import static jskills.numerics.MathUtils.square;
import static org.junit.Assert.assertEquals;

public class GaussianDistributionTests {
    private static final double ErrorTolerance = 0.000001;

    @Test
    public void CumulativeToTests() {
        // Verified with WolframAlpha
        // (e.g. http://www.wolframalpha.com/input/?i=CDF%5BNormalDistribution%5B0%2C1%5D%2C+0.5%5D )
        assertEquals(0.691462, GaussianDistribution.cumulativeTo(0.5), ErrorTolerance);
    }

    @Test
    public void AtTests() {
        // Verified with WolframAlpha
        // (e.g. http://www.wolframalpha.com/input/?i=PDF%5BNormalDistribution%5B0%2C1%5D%2C+0.5%5D )
        assertEquals(0.352065, GaussianDistribution.at(0.5), ErrorTolerance);
    }

    @Test
    public void MultiplicationTests() {
        // I verified this against the formula at
        // http://www.tina-vision.net/tina-knoppix/tina-memo/2003-003.pdf
        GaussianDistribution standardNormal = new GaussianDistribution(0, 1);
        GaussianDistribution shiftedGaussian = new GaussianDistribution(2, 3);

        GaussianDistribution product = GaussianDistribution.mult(standardNormal, shiftedGaussian);

        assertEquals(0.2, product.getMean(), ErrorTolerance);
        assertEquals(3.0 / sqrt(10), product.getStandardDeviation(), ErrorTolerance);

        GaussianDistribution m4s5 = new GaussianDistribution(4, 5);
        GaussianDistribution m6s7 = new GaussianDistribution(6, 7);

        GaussianDistribution product2 = GaussianDistribution.mult(m4s5, m6s7);

        double expectedMean = (4 * square(7) + 6 * square(5)) / (square(5) + square(7));
        assertEquals(expectedMean, product2.getMean(), ErrorTolerance);

        double expectedSigma = sqrt(((square(5) * square(7)) / (square(5) + square(7))));
        assertEquals(expectedSigma, product2.getStandardDeviation(), ErrorTolerance);
    }

    @Test
    public void DivisionTests() {
        // Since the multiplication was worked out by hand, we use the same
        // numbers but work backwards
        GaussianDistribution product = new GaussianDistribution(0.2, 3.0 / sqrt(10));
        GaussianDistribution standardNormal = new GaussianDistribution(0, 1);

        GaussianDistribution productDividedByStandardNormal = GaussianDistribution.divide(product, standardNormal);
        assertEquals(2.0, productDividedByStandardNormal.getMean(), ErrorTolerance);
        assertEquals(3.0, productDividedByStandardNormal.getStandardDeviation(), ErrorTolerance);

        GaussianDistribution product2 = new GaussianDistribution((4 * square(7) + 6 * square(5)) / (square(5) + square(7)), sqrt(((square(5) * square(7)) / (square(5) + square(7)))));
        GaussianDistribution m4s5 = new GaussianDistribution(4, 5);
        GaussianDistribution product2DividedByM4S5 = GaussianDistribution.divide(product2, m4s5);
        assertEquals(6.0, product2DividedByM4S5.getMean(), ErrorTolerance);
        assertEquals(7.0, product2DividedByM4S5.getStandardDeviation(), ErrorTolerance);
    }

    @Test
    public void LogProductNormalizationTests() {
        // Verified with Ralf Herbrich's F# implementation
        GaussianDistribution standardNormal = new GaussianDistribution(0, 1);
        double lpn = GaussianDistribution.logProductNormalization(standardNormal, standardNormal);
        assertEquals(-1.2655121234846454, lpn, ErrorTolerance);

        GaussianDistribution m1s2 = new GaussianDistribution(1, 2);
        GaussianDistribution m3s4 = new GaussianDistribution(3, 4);
        double lpn2 = GaussianDistribution.logProductNormalization(m1s2, m3s4);
        assertEquals(-2.5168046699816684, lpn2, ErrorTolerance);
    }

    @Test
    public void LogRatioNormalizationTests() {
        // Verified with Ralf Herbrich's F# implementation            
        GaussianDistribution m1s2 = new GaussianDistribution(1, 2);
        GaussianDistribution m3s4 = new GaussianDistribution(3, 4);
        double lrn = GaussianDistribution.logRatioNormalization(m1s2, m3s4);
        assertEquals(2.6157405972171204, lrn, ErrorTolerance);
    }

    @Test
    public void AbsoluteDifferenceTests() {
        // Verified with Ralf Herbrich's F# implementation            
        GaussianDistribution standardNormal = new GaussianDistribution(0, 1);
        double absDiff = GaussianDistribution.absoluteDifference(standardNormal, standardNormal);
        assertEquals(0.0, absDiff, ErrorTolerance);

        GaussianDistribution m1s2 = new GaussianDistribution(1, 2);
        GaussianDistribution m3s4 = new GaussianDistribution(3, 4);
        double absDiff2 = GaussianDistribution.absoluteDifference(m1s2, m3s4);
        assertEquals(0.4330127018922193, absDiff2, ErrorTolerance);
    }
}