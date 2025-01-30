public interface LinkedIterator<T> {
    // Checks if there is a next element in the iteration
    boolean hasNext();

    // Retrieves the next element in the iteration
    T getNext();

    // Resets the iterator to its initial state
    void reset();
}
