/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package ca.qc.johnabbott.cs406;

import com.company.collections.EmptySetException;
import com.company.collections.FullSetException;
import com.company.collections.SortedSet;
import com.company.collections.TraversalException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Unit tests for the SortedSet data structure
 * @author Ian Clement (ian.clement@johnabbott.qc.ca).
 */
public class SortedSetTest {

    // used to create
    private static final int TEST_SET_SIZE = 15;
    private static final int TEST_SET_CAPACITY = 20;

    // used to create random test data
    private static final long SEED = 123L;
    private static final int TEST_SET_MIN_ELEMENT = -50; // inclusive
    private static final int TEST_SET_MAX_ELEMENT = 50; // exclusive

    // the sample set used in multiple test cases
    private SortedSet<Integer> sampleSet;

    // keep track of the data in the set for testing purposes.
    private int[] expectedData;

    @Before
    public void setUp() {

        // fill sample set and array of expected data.
        sampleSet = new SortedSet<>(TEST_SET_CAPACITY);
        expectedData = new int[TEST_SET_SIZE];

        // ensure that we can make the set...
        if(TEST_SET_MAX_ELEMENT - TEST_SET_MIN_ELEMENT < TEST_SET_SIZE)
            throw new IllegalStateException("Impossible to make set.");

        Random random = new Random(SEED);
        int range = TEST_SET_MAX_ELEMENT - TEST_SET_MIN_ELEMENT;
        int count = 0;
        while(count < TEST_SET_SIZE) {
            int randomValue = random.nextInt(range) + TEST_SET_MIN_ELEMENT;

            // first check for duplicates (beware: very inefficient)
            boolean found = false;
            for(int i = 0; i < count && !found; i++) {
                if (expectedData[i] == randomValue)
                    found = true;
            }

            // only uniques elements are added to the set.
            if(!found) {
                expectedData[count++] = randomValue;
                sampleSet.add(randomValue);
            }
        }

        // sort the expected array for later testing.
        Arrays.sort(expectedData);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testContains() throws Exception {

        // all data can be found in the set.
        for (int x : expectedData)
            assertTrue(sampleSet.contains(x));

        // contains(..) returns false for values guaranteed to be out of set:
        //  lower than min and greater than max.
        int tooLow = TEST_SET_MIN_ELEMENT - 1;
        assertFalse(sampleSet.contains(tooLow));
        int tooHigh = TEST_SET_MAX_ELEMENT + 1;
        assertFalse(sampleSet.contains(tooHigh));

        // all values that are in range
        for(int value = TEST_SET_MIN_ELEMENT; value < TEST_SET_MAX_ELEMENT; value++) {
            if (Arrays.binarySearch(expectedData, value) < 0)
                assertFalse(sampleSet.contains(value));
        }
    }

    @Test
    public void testConstructorMakesEmptySet() {
        sampleSet = new SortedSet<>();
        assertTrue(sampleSet.isEmpty());
        assertEquals(0, sampleSet.size());
    }

    @Test
    public void testAddFirst() {
        assertTrue(sampleSet.add(TEST_SET_MIN_ELEMENT - 1));
        assertTrue(sampleSet.contains(TEST_SET_MIN_ELEMENT - 1));
        assertEquals(TEST_SET_SIZE + 1, sampleSet.size());
    }

    @Test
    public void testAddLast() {
        assertTrue(sampleSet.add(TEST_SET_MAX_ELEMENT + 1));
        assertTrue(sampleSet.contains(TEST_SET_MAX_ELEMENT + 1));
        assertEquals(TEST_SET_SIZE + 1, sampleSet.size());
    }

    @Test
    public void testAddMiddle() {
        int firstValue = expectedData[0];
        int lastValue = expectedData[TEST_SET_SIZE - 1];
        for (int value = firstValue + 1; value < lastValue; value++) {
            if (Arrays.binarySearch(expectedData, value) < 0) {
                assertTrue(sampleSet.add(value));
                assertTrue(sampleSet.contains(value));
                assertEquals(TEST_SET_SIZE + 1, sampleSet.size());
                return;
            }
        }
        fail();
    }

    @Test
    public void testNoDuplicates() {
        for(int x : expectedData){
            assertFalse(sampleSet.add(x));
            assertEquals(TEST_SET_SIZE, sampleSet.size());
        }
    }

    @Test
    public void testFullSet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        for(int i = 0; i < TEST_SET_CAPACITY; i++)
            set.add(i);
        assertEquals(TEST_SET_CAPACITY, set.size());
        assertTrue(set.isFull());
    }

    @Test (expected = FullSetException.class)
    public void testFullSetException() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        for(int i = 0; i < TEST_SET_CAPACITY + 1; i++)
            set.add(i);
    }

    @Test
    public void testAddDuplicateToFullSet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        for(int i = 0; i < TEST_SET_CAPACITY; i++)
            set.add(i);
        assertFalse(set.add(0));
        assertFalse(set.add(TEST_SET_CAPACITY - 1));
        assertFalse(set.add(TEST_SET_CAPACITY / 2));
    }

    @Test
    public void testRemoveFirst() {
        assertTrue(sampleSet.remove(expectedData[0]));
        assertFalse(sampleSet.contains(expectedData[0]));
        assertEquals(TEST_SET_SIZE-1, sampleSet.size());
    }

    @Test
    public void testRemoveLast() {
        assertTrue(sampleSet.remove(expectedData[TEST_SET_SIZE - 1]));
        assertFalse(sampleSet.contains(expectedData[TEST_SET_SIZE - 1]));
        assertEquals(TEST_SET_SIZE-1, sampleSet.size());
    }

    @Test
    public void testRemoveMiddle() {
        assertTrue(sampleSet.remove(expectedData[TEST_SET_SIZE/2]));
        assertFalse(sampleSet.contains(expectedData[TEST_SET_SIZE/2]));
        assertEquals(TEST_SET_SIZE - 1, sampleSet.size());

    }

    @Test
    public void testRemoveNotInSet() {
        for(int i = expectedData[0]; i < expectedData[TEST_SET_SIZE - 1]; i++) {
            if (Arrays.binarySearch(expectedData, i) > 0) {
                assertFalse(sampleSet.remove(expectedData[0] - 1));
                assertEquals(TEST_SET_SIZE, sampleSet.size());
                return;
            }
        }
        fail();
    }

    @Test
    public void testEmptySet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testRemoveOnEmptySet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        assertFalse(set.remove(42));
    }

    @Test
    public void testRemoveOnEmptySetNoThrow() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        try {
            set.remove(42);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testMin() throws Exception {
        assertEquals(expectedData[0], (int) sampleSet.min());  // not sure why I need the cast here...
    }

    @Test
    public void testMax() throws Exception {
        assertEquals(expectedData[TEST_SET_SIZE - 1], (int) sampleSet.max()); // same as above
    }

    @Test
    public void testNewMin() {
        sampleSet.add(expectedData[0] - 1);
        assertEquals(expectedData[0] - 1,  (int) sampleSet.min()); // same as above
    }

    @Test
    public void testNewMax() {
        sampleSet.add(expectedData[TEST_SET_SIZE - 1] + 1);
        assertEquals(expectedData[TEST_SET_SIZE - 1] + 1, (int) sampleSet.max()); // same as above
    }

    @Test
    public void testMinMaxInSingletonSet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        set.add(42);
        assertEquals(set.min(), set.max());
    }

    @Test (expected = EmptySetException.class)
    public void testMinOnEmptySet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        set.min();
    }

    @Test (expected = EmptySetException.class)
    public void testMaxOnEmptySet() {
        SortedSet<Integer> set = new SortedSet<>(TEST_SET_CAPACITY);
        set.max();
    }

    @Test
    public void testTraversal() {
        sampleSet.reset();
        for(int x : expectedData) {
            assertTrue(sampleSet.hasNext());
            assertEquals(x, (int) sampleSet.next());
        }
        assertFalse(sampleSet.hasNext());
    }

    @Test
    public void testTraversalWithoutError() {
        sampleSet.reset();
        try {
            while (sampleSet.hasNext())
                sampleSet.next();
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTraversalEmptySet() {
        SortedSet<Integer> set = new SortedSet<>();
        set.reset();
        assertFalse(set.hasNext());
    }

    @Test (expected = TraversalException.class)
    public void testAddDuringTraversal() {
        sampleSet.reset();
        sampleSet.next();
        sampleSet.add(123);
        sampleSet.next();
    }

    @Test (expected = TraversalException.class)
    public void testAddRemoveDuringTraversal() {
        sampleSet.reset();
        sampleSet.next();
        sampleSet.add(123);
        sampleSet.remove(123);
        sampleSet.next();
    }

    @Test (expected = TraversalException.class)
    public void testCallNextOnCompletedTraversal() {
        sampleSet.reset();
        while(sampleSet.hasNext())
            sampleSet.next();
        sampleSet.next();
    }

    @Test
    public void testAddAfterAbandonedTraversal() {
        sampleSet.reset();
        sampleSet.next();
        sampleSet.next();
        sampleSet.add(123);
    }

    @Test
    public void testContainsAll() {
        SortedSet<Integer> subset = new SortedSet<>(expectedData.length);
        for (int i = 0; i < expectedData.length; i += 2)
            subset.add(expectedData[i]);

        assertTrue(sampleSet.containsAll(subset));
        assertFalse(subset.containsAll(sampleSet));
    }

    @Test
    public void testContainsAllEmptySet() {
        // empty set is subset of all sets
        SortedSet<Integer> empty = new SortedSet<>();
        assertTrue(sampleSet.containsAll(empty));
        assertFalse(empty.containsAll(sampleSet));
    }


    @Test
    public void testEqualSets() {
        // equal sets are subsets of each other
        SortedSet<Integer> same = new SortedSet<>(expectedData.length);
        for (int i = 0; i < expectedData.length; i++)
            same.add(expectedData[i]);

        assertTrue(sampleSet.containsAll(same));
        assertTrue(same.containsAll(sampleSet));

        // another way this might occur:
        assertTrue(sampleSet.containsAll(sampleSet));
    }

    @Test
    public void testToString() {

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean first = true;
        for(int x : expectedData) {
            if(first)
                first = false;
            else
                sb.append(", ");
            sb.append(x);
        }
        sb.append('}');

        assertEquals(sb.toString(), sampleSet.toString());

        sampleSet = new SortedSet<>(TEST_SET_CAPACITY);
        assertEquals("{}", sampleSet.toString());

        sampleSet.add(123);
        assertEquals("{123}", sampleSet.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubsetIllegalArguments() {
        sampleSet.subset(100, 99);
    }

    @Test
    public void testSubsetOnEmptySet() {
        SortedSet<Integer> set = new SortedSet<>();
        SortedSet<Integer> subset = set.subset(0, 1000);
        assertTrue(subset.isEmpty());
    }

    @Test
    public void testSubsetLowEqualsHigh() {
        // low = high gives empty set
        int value = expectedData[TEST_SET_SIZE / 2];
        SortedSet<Integer> empty = sampleSet.subset(value, value);
        assertTrue(empty.isEmpty());
    }

    @Test
    public void testSubset() {

        // test subset for the three innermost elements
        int low = expectedData[TEST_SET_SIZE / 2 - 1] - 1;
        int high = expectedData[TEST_SET_SIZE / 2 + 1] + 1;

        SortedSet<Integer> subset = sampleSet.subset(low, high);
        assertEquals(3, subset.size());
        assertTrue(subset.contains(expectedData[TEST_SET_SIZE / 2 - 1]));
        assertTrue(subset.contains(expectedData[TEST_SET_SIZE / 2]));
        assertTrue(subset.contains(expectedData[TEST_SET_SIZE / 2 + 1]));
    }

    @Test
    public void testSubsetHighValueExclusive() {
        // test subset for the two innermost elements
        int low = expectedData[TEST_SET_SIZE / 2 - 1] - 1;
        int high = expectedData[TEST_SET_SIZE / 2 + 1];

        // test subset with exclusive
        SortedSet<Integer> subset = sampleSet.subset(expectedData[TEST_SET_SIZE / 2 - 1], expectedData[TEST_SET_SIZE / 2 + 1]);
        assertEquals(2, subset.size());
        assertTrue(subset.contains(expectedData[TEST_SET_SIZE / 2 - 1]));
        assertTrue(subset.contains(expectedData[TEST_SET_SIZE / 2]));
    }

    @Test
    public void testSubsetIsEntireSet() {
        // subset is the same as the set if the entire range is specified
        SortedSet<Integer> subset = sampleSet.subset(expectedData[0] - 1, expectedData[TEST_SET_SIZE - 1] + 1);
        assertEquals(sampleSet.size(), subset.size());
        assertTrue(sampleSet.containsAll(subset));
        assertTrue(subset.containsAll(sampleSet));
    }

}