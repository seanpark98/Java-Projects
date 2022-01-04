public class OffByOne implements CharacterComparator {
    /** Checks if two characters are off by one */
    @Override
    public boolean equalChars(char x, char y) {
        int a = (int) x;
        int b = (int) y;
        return (Math.abs(a - b)) == 1;
    }
}
