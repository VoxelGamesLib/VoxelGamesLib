package jskills.trueskill;

import org.junit.Before;
import org.junit.Test;

public class TwoPlayerTrueSkillCalculatorTest {

    private TwoPlayerTrueSkillCalculator calculator;

    @Before
    public void setup() {
        calculator = new TwoPlayerTrueSkillCalculator();
    }

    @Test
    public void TestAllTwoPlayerScenarios() {
        // We only support two players
        TrueSkillCalculatorTests.TestAllTwoPlayerScenarios(calculator);
    }

    // TODO: Assert failures for larger teams
}