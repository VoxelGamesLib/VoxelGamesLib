package jskills;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RankSorterTest {
    @Test
    public void sortAlreadySortedTest() {
        List<String> people = Arrays.asList(new String[]{"One", "Two", "Three"});
        int[] ranks = new int[]{1, 2, 3},
                sortedranks = new int[]{1, 2, 3};

        people = RankSorter.sort(people, ranks);

        // assertEquals doesn't work on primitive arrays
        // see http://code.google.com/p/testng/issues/detail?id=4
        for (int i = 0; i < people.size(); i++)
            assertEquals(String.format("Different at index %d. Expected <%d>, was <%d>.", i, sortedranks[i], ranks[i]), ranks[i], sortedranks[i]);
        assertEquals(people, Arrays.asList(new String[]{"One", "Two", "Three"}));
    }

    @Test
    public void sortUnsortedTest() {
        List<String> people = Arrays.asList(new String[]{"Five", "Two1", "Two2", "One", "Four"});
        int[] ranks = new int[]{5, 2, 2, 1, 4},
                sortedranks = new int[]{1, 2, 2, 4, 5};

        people = RankSorter.sort(people, ranks);

        // assertEquals doesn't work on primitive arrays
        // see http://code.google.com/p/testng/issues/detail?id=4
        for (int i = 0; i < people.size(); i++)
            assertEquals(String.format("Different at index %d. Expected <%d>, was <%d>.", i, sortedranks[i], ranks[i]), ranks[i], sortedranks[i]);
        assertEquals(people, Arrays.asList(new String[]{"One", "Two1", "Two2", "Four", "Five"}));
    }
}