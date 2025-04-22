import java.io.Serializable;

/**
 * Message class for client-server and server-server communication.
 * Supports distributed linked list operations and origin tracking.
 *
 * COSC 2454 – DDS Project
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Operation type: e.g., ADD, DELETE, INSERT, VIEW, COMMIT, ROLLBACK, EXIT */
    public String operation;

    /** Value associated with the operation (e.g., list item, index/value pair) */
    public String value;

    /** Flag to indicate if this message was forwarded from another server */
    public boolean fromServer;

    /**
     * Constructor for messages with operation and value.
     *
     * @param operation Type of operation (e.g., ADD, DELETE)
     * @param value Associated value or data
     */
    public Message(String operation, String value) {
        this.operation = operation;
        this.value = value;
        this.fromServer = false;
    }

    /**
     * Constructor for simple operations with no value (e.g., VIEW, EXIT).
     *
     * @param operation The type of operation
     */
    public Message(String operation) {
        this(operation, "");
    }

    /**
     * Full constructor with fromServer flag.
     *
     * @param operation The type of operation
     * @param value The associated value
     * @param fromServer True if forwarded from a server, false if from a client
     */
    public Message(String operation, String value, boolean fromServer) {
        this.operation = operation;
        this.value = value;
        this.fromServer = fromServer;
    }

    @Override
    public String toString() {
        return "[Operation: " + operation + ", Value: " + value + ", FromServer: " + fromServer + "]";
    }
}
