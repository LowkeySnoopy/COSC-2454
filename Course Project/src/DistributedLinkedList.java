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

    public void add(String value) {
        list.add(value);
    }

    public boolean delete(String value) {
        return list.remove(value);
    }

    public List<String> getData() {
        return list;
    }

    public void clear() {
        list.clear();
    }

    public void setData(List<String> newData) {
        list.clear();
        list.addAll(newData);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
