public class LinkedListDeque<T> {

    /** The Nested Node class */
    private class AnyNode {
        private AnyNode prev;
        private T item;
        private AnyNode next;

        private AnyNode() {
            prev = null;
            item = null;
            next = null;
        }
        private AnyNode(T i) {
            prev = null;
            item = i;
            next = null;
        }
    }
    private AnyNode sentinel;
    private int size;

    /** Creates empty LinkedList */
    public LinkedListDeque() {
        sentinel = new AnyNode();
        size = 0;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    /** Adds item to first of list */
    public void addFirst(T item) {
        AnyNode addednode = new AnyNode(item);
        addednode.next = sentinel.next;
        addednode.prev = sentinel;
        sentinel.next = addednode;
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }
    /** Adds item to last of list */
    public void addLast(T item) {
        AnyNode addednode = new AnyNode(item);
        addednode.next = sentinel;
        addednode.prev = sentinel.prev;
        sentinel.prev = addednode;
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }
    /** Checks if list is empty */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    /** Computes the size of the list */
    public int size() {
        int reval = 0;
        AnyNode p = sentinel;
        while (p.next != sentinel) {
            reval += 1;
            p = p.next;
        }
        return reval;
    }
    /** Prints the list one by one on a separate line */
    public void printDeque() {
        AnyNode l = sentinel.next;
        while (l != sentinel) {
            System.out.print(l.item + " ");
            l = l.next;
        }
        System.out.println();
    }
    /** Removes the first item of the list and returns it */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T reitem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return reitem;
    }
    /** Removes the last item of the list and returns it */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T reitem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return reitem;
    }
    public T get(int index) {
        AnyNode k = sentinel.next;
        int i = 0;
        while (i < index) {
            k = k.next;
            i += 1;
        }
        return k.item;
    }
    /** Gets the nth element recursively */
    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }

    /** Helper method of get recursive */
    private T getRecursive(int i, AnyNode currnode) {
        if (i == 0) {
            return currnode.item;
        }
        return getRecursive(i - 1, currnode.next);
    }
    /** Deep copies a list */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new AnyNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
        int j = 0;
        while (j < other.size) {
            addLast((T) other.get(j));
            j += 1;
        }
    }


}
