import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Responsible for logging updates to either client_updates.log or server_updates.log.
 *
 * COSC 2454 – DDS Project
 */
public class LogWriter {

    private static final String CLIENT_LOG = "client_updates.log";
    private static final String SERVER_LOG = "server_updates.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs an update to the appropriate file.
     *
     * @param sourceIP IP address of the client or server that sent the update
     * @param operation Type of operation (ADD, DELETE, etc.)
     * @param value Associated value (list item)
     * @param isFromServer True if the update came from a server; false if from a client
     */
    public static void logUpdate(InetAddress sourceIP, String operation, String value, boolean isFromServer) {
        String logEntry = String.format("<%s, %s: %s, %s>",
                sourceIP.getHostAddress(),
                operation,
                value,
                LocalDateTime.now().format(FORMATTER)
        );

        String filename = isFromServer ? SERVER_LOG : CLIENT_LOG;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("LogWriter error: " + e.getMessage());
        }
    }
}
