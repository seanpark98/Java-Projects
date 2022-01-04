public interface Deque<T> {
    /** Adds First to the Deque */
    void addFirst(T x);
    /** Adds last to the Deque */
    void addLast(T y);
    /** Checks if Deque is empty */
    default boolean isEmpty() {
        return size() == 0;
    }
    /** Returns size of Deque */
    int size();
    /** Prints Deque */
    void printDeque();
    /** Removes first element and returns it */
    T removeFirst();
    /** Removes last element and returns it */
    T removeLast();
    /** Gets element at nth index */
    T get(int index);



}
