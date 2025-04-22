import java.util.ArrayList;
import java.util.List;

/**
 * ClientUpdate class handles local update operations before they are sent to the server.
 *
 * COSC 2454 – DDS Project
 */
public class ClientUpdate {

    private final List<String> localList;

    public ClientUpdate() {
        localList = new ArrayList<>();
    }

    public void add(String item) {
        localList.add(item);
        System.out.println("Locally added: " + item);
    }

    public void delete(String item) {
        if (localList.remove(item)) {
            System.out.println("Locally deleted: " + item);
        } else {
            System.out.println("Item not found locally: " + item);
        }
    }

    public void clear() {
        localList.clear();
        System.out.println("Local list cleared.");
    }

    public void print() {
        System.out.println("Local List: " + localList);
    }

    public List<String> getLocalList() {
        return localList;
    }
}
