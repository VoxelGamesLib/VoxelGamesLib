package jskills;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RankSorterTest {
    @Test
    public void sortAlreadySortedTest() {
        List<String> people = Arrays.asList("One", "Two", "Three");
        int[] ranks = {1, 2, 3};
        int[] sortedRanks = {1, 2, 3};

        people = RankSorter.sort(people, ranks);

        // assertEquals doesn't work on primitive arrays
        // see http://code.google.com/p/testng/issues/detail?id=4
        for (int i = 0; i < people.size(); i++) {
            assertEquals(String.format("Different at index %d. Expected <%d>, was <%d>.", i, sortedRanks[i], ranks[i]), ranks[i], sortedRanks[i]);
        }
        assertEquals(people, Arrays.asList("One", "Two", "Three"));
    }

    @Test
    public void sortUnsortedTest() {
        List<String> people = Arrays.asList("Five", "Two1", "Two2", "One", "Four");
        int[] ranks = {5, 2, 2, 1, 4};
        int[] sortedRanks = {1, 2, 2, 4, 5};

        people = RankSorter.sort(people, ranks);

        // assertEquals doesn't work on primitive arrays
        // see http://code.google.com/p/testng/issues/detail?id=4
        for (int i = 0; i < people.size(); i++) {
            assertEquals(String.format("Different at index %d. Expected <%d>, was <%d>.", i, sortedRanks[i], ranks[i]), ranks[i], sortedRanks[i]);
        }
        assertEquals(people, Arrays.asList("One", "Two1", "Two2", "Four", "Five"));
    }
}