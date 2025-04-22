import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * ClientLog class logs every operation made on the client side.
 *
 * COSC 2454 – DDS Project
 */
public class ClientLog {

    private final String logFile;

    public ClientLog(String filename) {
        this.logFile = filename;
    }

    public void writeLog(String operation, String value) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            String timestamp = LocalDateTime.now().toString();
            writer.printf("[%s] Operation: %s, Value: %s%n", timestamp, operation, value);
        } catch (IOException e) {
            System.err.println("Error writing to log: " + e.getMessage());
        }
    }
}
