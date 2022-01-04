public class ArrayDeque<T> {
    /** instance variables */
    private T[] items;
    private int size;
    private int front;
    private int back;
    /** Creating empty ArrayDeque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 1;
    }
    /** Creates a deep copy of ArrayDeque */
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 1;
        int j = 0;
        while (j < other.size) {
            addLast((T) other.get(j));
            j += 1;
        }
    }
    /** Resizes the array */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int start = front;
        int copier = 0;
        while (copier < size) {
            start = readjback(start);
            a[copier] = items[start];
            copier += 1;
        }
        items = a;
        front = items.length - 1;
        back = size;
    }
    /** Readjusts where next front item should be added */
    private int readjfront(int x) {
        if (x <= 0) {
            return items.length - 1;
        }
        return x - 1;
    }
    /** Readjusts where next last item should be added */
    private int readjback(int y) {
        if (y == items.length - 1) {
            return 0;
        }
        return y + 1;
    }
    /** Adds to the first of the list */
    public void addFirst(T item) {
        items[front] = item;
        front = readjfront(front);
        size += 1;
        if (size == items.length) {
            resize(size * 2);
        }
    }
    /** Adds to the last of the list */
    public void addLast(T item) {
        items[back] = item;
        back = readjback(back);
        size += 1;
        if (size == items.length) {
            resize(size * 2);
        }
    }
    /** Removes the first element and returns the element */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T retval = items[readjback(front)];
        items[readjback(front)] = null;
        front = readjback(front);
        size -= 1;
        /** Taken from book 2.5 to downsize */
        if (size <= items.length / 4) {
            resize(items.length / 2);
        }
        return retval;
    }
    /** Removes the last element and returns the element */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T retval = items[readjfront(back)];
        items[readjfront(back)] = null;
        back = readjfront(back);
        size -= 1;
        /** Taken from book 2.5 to downsize */
        if (size <= items.length / 4) {
            resize(items.length / 2);
        }
        return retval;
    }
    /** Returns the nth element of the list */
    public T get(int index) {
        return get(index, readjback(front));
    }
    /** Helper recursive method for get */
    private T get(int index, int pos) {
        if (index == 0) {
            T reval = items[pos];
            return reval;
        }
        return get(index - 1, readjback(pos));
    }

    /** Returns true if ArrayDeque is empty; Otherwise, False */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    /** Returns size of ArrayDeque */
    public int size() {
        return size;
    }
    /** Prints the ArrayDeque */
    public void printDeque() {
        int i = readjback(front);
        while (i < readjfront(back)) {
            System.out.print(items[i] + " ");
            i++;
        }
        System.out.println();
    }
}

