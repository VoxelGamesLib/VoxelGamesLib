package jskills.trueskill;

import org.junit.Before;
import org.junit.Test;

public class TwoTeamTrueSkillCalculatorTest {

    private TwoTeamTrueSkillCalculator calculator;

    @Before
    public void setup() {
        calculator = new TwoTeamTrueSkillCalculator();
    }

    @Test
    public void TestAllTwoPlayerScenarios() {
        // This calculator supports up to two teams with many players each
        TrueSkillCalculatorTests.TestAllTwoPlayerScenarios(calculator);
    }

    @Test
    public void TestAllTwoTeamScenarios() {
        // This calculator supports up to two teams with many players each
        TrueSkillCalculatorTests.TestAllTwoTeamScenarios(calculator);
    }
}