public class Palindrome {
    /** Makes the word into a Deque */
    public Deque<Character> wordToDeque(String word) {
        String toturn = word;
        Deque<Character> dtr = new LinkedListDeque<Character>();
        for (int i = 0; i < toturn.length(); i++) {
            char chartoadd = toturn.charAt(i);
            dtr.addLast(chartoadd);
        }
        return dtr;
    }
    /** Checks if word is palindrome */
    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> ogword = wordToDeque(word);
        String tocompare = word;
        Deque<Character> dtc = new LinkedListDeque<Character>();
        for (int i = 0; i < tocompare.length(); i++) {
            char chartoadd = tocompare.charAt(i);
            dtc.addFirst(chartoadd);
        }
        return isPalindrome(ogword, dtc);
    }
    /** helper method for palindrome */
    private boolean isPalindrome(Deque x, Deque y) {
        while (x.size() > 0 && y.size() > 0) {
            Object c = x.removeFirst();
            Object d = y.removeFirst();
            if (c != d) {
                return false;
            } else {
                return isPalindrome(x, y);
            }
        }
        return true;
    }
    /** overload palindrome method for checking if word is palindrome
     * according to the character comparator */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        int wl = word.length() - 1;
        int iter = word.length() / 2;
        int curr = 0;
        while (curr < iter) {
            char c1 = word.charAt(curr);
            char c2 = word.charAt(wl);
            if (!cc.equalChars(c1, c2)) {
                return false;
            }
            curr += 1;
            wl -= 1;
        }
        return true;
    }
}
