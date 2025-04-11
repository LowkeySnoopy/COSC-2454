// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/13/2025
// Randomized Queue
/* The Randomized Queue code implements a queue where items can be added, removed, and sampled randomly.
It uses an array to store the items and allows for resizing as needed.
The queue supports random iteration, where an iterator returns items in a random order,
and it dynamically adjusts its capacity when items are added or removed.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue; // Array to store items
    private int size; // Number of elements in the queue
    private final Random random; // Random number generator

    // Initializes an empty queue with an initial capacity of 2
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
        random = new Random();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of items in the queue
    public int size() {
        return size;
    }

    // Resize the queue when needed
    private void resize(int capacity) {
        Item[] newQueue = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    // Add an item to the queue
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null item");
        if (size == queue.length) resize(2 * queue.length); // Double the array size if full
        queue[size++] = item;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int randIndex = random.nextInt(size); // Select a random index
        Item item = queue[randIndex];
        queue[randIndex] = queue[size - 1]; // Swap with last item
        queue[size - 1] = null; // Prevent memory leak
        size--;
        if (size > 0 && size == queue.length / 4) resize(queue.length / 2); // Shrinks array if needed
        return item;
    }

    // Return a random item without removing it
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int randIndex = random.nextInt(size); // Select a random index
        return queue[randIndex];
    }

    // Return an iterator that iterates randomly over the items
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private final Item[] shuffledQueue;
        private int current;

        // Creates a shuffled copy of the queue for iteration
        public RandomizedIterator() {
            shuffledQueue = (Item[]) new Object[size];
            System.arraycopy(queue, 0, shuffledQueue, 0, size);
            shuffle(shuffledQueue); // Shuffle the items for random order iteration
            current = 0;
        }

        // Shuffle the array
        private void shuffle(Item[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int randIndex = random.nextInt(i + 1);
                Item temp = array[i];
                array[i] = array[randIndex];
                array[randIndex] = temp;
            }
        }

        // Check if there are more elements in the iterator
        public boolean hasNext() {
            return current < shuffledQueue.length;
        }

        // Return the next item in the iteration
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffledQueue[current++];
        }
    }

    // Unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        // Enqueue items
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);

        // Sample without removing
        System.out.println("Sample: " + rq.sample());
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();
        System.out.println("Remaining size: " + rq.size());

        // Dequeue and print removed item
        System.out.println("Dequeued: " + rq.dequeue());
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();
        System.out.println("Remaining size: " + rq.size());

        // Demonstrate multiple iterators producing independent random orders
        System.out.println("Iterator 1:");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        System.out.println("Iterator 2:");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        System.out.println("Iterator 3:");
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();

        // Dequeue another item and check remaining size
        System.out.println("Dequeued: " + rq.dequeue());
        for (int item : rq) {
            System.out.print(item + " ");
        }
        System.out.println();
        System.out.println("Remaining size: " + rq.size());
        
    }
}
