import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Wrapper class for a distributed version of a linked list.
 * Provides safe access and update methods.
 */
public class DistributedLinkedList implements Serializable {

    private final List<String> list;

    public DistributedLinkedList() {
        this.list = new LinkedList<>();
    }

    /**
     * Adds an item to the end of the list.
     */
    public void add(String value) {
        list.add(value);
    }

    /**
     * Deletes the first occurrence of the specified value.
     */
    public boolean delete(String value) {
        return list.remove(value);
    }

    /**
     * Inserts a value at a specified index.
     *
     * @param index the position to insert into
     * @param value the value to insert
     * @return true if successful, false if index is invalid
     */
    public boolean insert(int index, String value) {
        try {
            list.add(index, value);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Returns the current list data.
     */
    public List<String> getData() {
        return list;
    }

    /**
     * Clears the entire list.
     */
    public void clear() {
        list.clear();
    }

    /**
     * Replaces the current list with a new one.
     */
    public void setData(List<String> newData) {
        list.clear();
        list.addAll(newData);
    }

    /**
     * Returns the list as a string.
     */
    @Override
    public String toString() {
        return list.toString();
    }
}
