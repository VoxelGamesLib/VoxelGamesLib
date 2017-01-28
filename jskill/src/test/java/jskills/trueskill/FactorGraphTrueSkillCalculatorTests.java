package jskills.trueskill;

import org.junit.Before;
import org.junit.Test;

public class FactorGraphTrueSkillCalculatorTests {

    private FactorGraphTrueSkillCalculator calculator;

    @Before
    public void setup() {
        calculator = new FactorGraphTrueSkillCalculator();
    }

    @Test
    public void TestAllTwoTeamScenarios() {
        TrueSkillCalculatorTests.TestAllTwoTeamScenarios(calculator);
    }

    @Test
    public void TestAllTwoPlayerScenarios() {
        TrueSkillCalculatorTests.TestAllTwoPlayerScenarios(calculator);
    }

    @Test
    public void TestAllMultipleTeamScenarios() {
        TrueSkillCalculatorTests.TestAllMultipleTeamScenarios(calculator);
    }

    @Test
    public void TestPartialPlayScenarios() {
        TrueSkillCalculatorTests.TestPartialPlayScenarios(calculator);
    }
}