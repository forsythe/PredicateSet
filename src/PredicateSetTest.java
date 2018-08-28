import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PredicateSetTest {

    private PredicateSet<Integer> intSet;

    @BeforeEach
    void setUp() {
        intSet = new PredicateSet<>();
    }

    @Test
    void size() {
        intSet.add(1);
        intSet.add(2);
        intSet.add(3);
        intSet.add(3);
        intSet.add(2);
        assertEquals(3, intSet.size());
    }

    @Test
    void isEmpty() {
        assertTrue(intSet.isEmpty());
        intSet.add(1);
        assertFalse(intSet.isEmpty());
    }

    @Test
    void contains() {
        intSet.add(1);
        assertTrue(intSet.contains(1));
        assertFalse(intSet.contains(2));
        intSet.add(2);
        assertTrue(intSet.contains(1));
        assertTrue(intSet.contains(2));
    }

    @Test
    void remove() {
        intSet.add(1);
        assertTrue(intSet.contains(1));

        intSet.add(2);
        assertTrue(intSet.contains(2));

        assertFalse(intSet.remove(5));

        assertTrue(intSet.remove(1));
        assertFalse(intSet.contains(1));
        assertEquals(1, intSet.size());
    }

    @Test
    void containsAll() {
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertTrue(intSet.containsAll(Arrays.asList(1, 2, 3, 4)));
        assertFalse(intSet.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        assertTrue(intSet.add(6));
        assertTrue(intSet.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
        assertTrue(intSet.remove(6));
        assertFalse(intSet.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
    }

    @Test
    void addAll() {
        assertTrue(intSet.addAll(Arrays.asList(1, 2, 3, 4, 5)));
        assertFalse(intSet.addAll(Arrays.asList(1, 2, 3, 4, 5)));
        assertEquals(5, intSet.size());
        intSet.addAll(Arrays.asList(1, 5, 6));
        assertEquals(6, intSet.size());
    }

    @Test
    void removeAll() {
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertFalse(intSet.removeAll(Arrays.asList(100, 200, 300)));
        assertTrue(intSet.removeAll(Arrays.asList(100, 2, 3, 4)));
        assertEquals(2, intSet.size());
    }

    @Test
    void intersect() {
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertTrue(intSet.intersect(Arrays.asList(2, 200, 300, 100)));
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertFalse(intSet.intersect(Arrays.asList(1, 2, 3, 4, 5)));
        assertTrue(intSet.intersect(Arrays.asList(2, 3, 100)));
        assertEquals(2, intSet.size());
    }

    @Test
    void clear() {
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        intSet.clear();
        assertEquals(0, intSet.size());
    }

    @Test
    void setMinus(){
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertFalse(intSet.setMinus(Arrays.asList(200, 300, 100)));
        assertTrue(intSet.setMinus(Arrays.asList(2, 3, 4, 10)));
        assertEquals(2, intSet.size());

    }

    @Test
    void union(){
        intSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        assertTrue(intSet.union(Arrays.asList(2, 3, 10, 12)));
        assertEquals(7, intSet.size());
        assertFalse(intSet.union(Arrays.asList(2, 3, 10, 12)));
    }
}