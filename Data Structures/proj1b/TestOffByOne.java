import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    // Your tests go here.
    /** Tests equalChars method in OffbyOne.java */
    @Test
    public void testequalChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertFalse(offByOne.equalChars('a', 'z'));
        assertFalse(offByOne.equalChars('c', 'e'));
        assertFalse(offByOne.equalChars('d', 'd'));
        assertFalse(offByOne.equalChars('A', 'b'));
        assertTrue(offByOne.equalChars('C', 'D'));
        assertTrue(offByOne.equalChars('K', 'L'));
        assertFalse(offByOne.equalChars('Z', 'y'));
        assertFalse(offByOne.equalChars('W', 'S'));
    }
}
