import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;
    private Deque<Item> _this = this;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        failNullArgument(item);
        Node newFirst = new Node(item);
        if (first != null) {
            newFirst.tail = first;
            first.head = newFirst;
        }
        first = newFirst;
        if (last == null) {
            last = first;
        }
        size++;
    }

    public void addLast(Item item) {
        failNullArgument(item);
        Node newLast = new Node(item);
        if (last != null) {
            last.tail = newLast;
            newLast.head = last;
        } else {
            first = null;
        }
        last = newLast;
        if (first == null) {
            first = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("No element found");
        }
        Node oldFirst = first;
        Node newFirst = first.tail;
        if (newFirst != null) {
            newFirst.head = null;
            oldFirst.tail = null;
            first = newFirst;
        } else {
            clearQueue();
        }
        size--;
        return oldFirst.item;
    }

    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("No element found");
        }
        Node oldLast = last;
        Node newLast = last.head;
        if (newLast != null) {
            newLast.tail = null;
            oldLast.head = null;
            last = newLast;
        } else {
            clearQueue();
        }
        size--;
        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        System.out.println("Adding string 'foo' to first");
        dq.addFirst("foo");
        System.out.println(String.format("Size is %d", dq.size()));
        System.out.println("Adding string 'bar' to last");
        dq.addLast("bar");
        System.out.println(String.format("Size is %d", dq.size()));
        System.out.println("Iterating items");
        for (String i : dq) {
            System.out.println(String.format("Item found %s", i));
        }
        System.out.println("Removing last...");
        String last = dq.removeLast();
        System.out.println(String.format("Got %s...", last));
        System.out.println(String.format("Size is %d", dq.size()));
        System.out.println("Removing first...");
        String first = dq.removeFirst();
        System.out.println(String.format("Got %s...", first));
        System.out.println(String.format("Size is %d", dq.size()));
    }

    private void clearQueue() {
        first = null;
        last = null;
    }

    private void failNullArgument(Item arg) {
        if (arg == null) {
            throw new IllegalArgumentException("Invalid argument");
        }
    }

    private class ListIterator implements Iterator<Item> {
        final private Deque<Item> dequeue = _this;
        private Node pointer = null;

        public boolean hasNext() {
            if (dequeue.isEmpty()) {
                return false;
            }
            if (pointer == null) {
                return true;
            }
            return pointer.tail != null;
        }

        public Item next() {
            if (pointer == null) {
                pointer = dequeue.first;
            } else {
                pointer = pointer.tail;
            }
            if (pointer == null) {
                throw new NoSuchElementException("No next element");
            }
            return pointer.item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Method not supported");
        }
    }

    private class Node {
        public Item item;
        public Node head;
        public Node tail;

        public Node(Item i) {
            item = i;
        }
    }
}