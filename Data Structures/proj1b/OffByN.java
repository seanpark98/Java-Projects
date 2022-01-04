public class OffByN implements CharacterComparator {
    private int n;

    /** Constructor for OffByN */
    public OffByN(int N) {
        n = N;
    }
    /** Checks if two characters are off by N */
    @Override
    public boolean equalChars(char x, char y) {
        int a = (int) x;
        int b = (int) y;
        return (Math.abs(a - b)) == n;
    }
}
