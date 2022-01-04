import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator obn = new OffByN(4);
    /** Tests equalChars method in OffByN.java */
    @Test
    public void testequalChars() {
        assertTrue(obn.equalChars('a', 'e'));
        assertTrue(obn.equalChars('b', 'f'));
        assertTrue(obn.equalChars('v', 'z'));
        assertFalse(obn.equalChars('a', 'b'));
        assertFalse(obn.equalChars('c', 'e'));
        assertFalse(obn.equalChars('d', 'd'));
    }

}
