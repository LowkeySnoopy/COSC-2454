import java.io.*;
import java.util.List;

/**
 * DiskManager handles serialization and deserialization
 * of the in-memory linked list to and from disk.
 *
 * COSC 2454 – DDS Project
 */
public class DiskManager {

    private static final String DATA_FILE = "data_version.ser";

    /**
     * Commits the current list to disk by serializing it.
     *
     * @param list The in-memory list to save
     * @return true if commit was successful
     */
    public static boolean commit(List<String> list) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(list);
            return true;
        } catch (IOException e) {
            System.err.println("Commit failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Rolls back to the last committed version from disk.
     *
     * @return The deserialized list or null if rollback fails
     */
    @SuppressWarnings("unchecked")
    public static List<String> rollback() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (List<String>) in.readObject();
        } catch (Exception e) {
            System.err.println("Rollback failed: " + e.getMessage());
            return null;
        }
    }
}
