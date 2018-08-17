import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Item[] queue;

    public RandomizedQueue() {
        queue = createGenericArray(0);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        if (size == queue.length) {
            resize(2 * queue.length);
        }
        queue[size++] = item;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Illegal argument");
        }
        int index = getRandomIndex();
        Item item = queue[index];
        queue[index] = queue[size - 1];
        queue[--size] = null;
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Illegal argument");
        }
        return queue[getRandomIndex()];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
         rq.enqueue(23);
         rq.enqueue(5);
         rq.isEmpty();
         rq.size();
         rq.isEmpty();
         rq.dequeue();
         rq.enqueue(30);
         rq.enqueue(18);
         rq.dequeue();
         rq.dequeue();
         rq.dequeue();
    }

    private int getRandomIndex() {
        return StdRandom.uniform(size);
    }

    private Item[] createGenericArray(int n) {
        return (Item[]) new Object[n];
    }

    private void resize(int capacity) {
        if (capacity == 0) {
            capacity = 1;
        }
        Item[] copy = createGenericArray(capacity);
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    private class RandomIterator implements Iterator<Item> {
        final private Item[] shuffledQueue;
        private int pointer;

        public RandomIterator() {
            shuffledQueue = createGenericArray(size);
            for (int i = 0; i < size; i++) {
                shuffledQueue[i] = queue[i];
            }
            shuffle();
        }

        public boolean hasNext() {
            if (shuffledQueue.length == 0) {
                return false;
            }
            if (pointer > shuffledQueue.length - 1) {
                return false;
            }
            return true;
        }

        public Item next() {
            if(!hasNext()){throw new java.util.NoSuchElementException("No next element");}
            return queue[pointer++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Method not supported");
        }

        private void shuffle() {
            for (int i = 0; i < size; i++) {
                int pos = StdRandom.uniform(size);
                Item temp = queue[i];
                queue[i] = queue[pos];
                queue[pos] = temp;
            }
        }
    }
}