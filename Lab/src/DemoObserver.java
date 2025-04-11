// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
/*
 * This program implements an Observer for managing file operations
 * such as opening, saving, and deleting files, while notifying subscribed observers
 * about these actions. Unlike a simulated approach, this version physically creates,
 * modifies, and deletes files using java.io.File within the operating system.
 * The Editor class handles file operations and uses an EventManager to notify observers,
 * such as LogOpenListener, EmailNotificationListener, and DeleteFileListener.
 * The program supports dynamic subscription and unsubscription of event listeners,
 * ensuring notifications can be enabled or disabled as needed.
 * Additionally, a custom exception (COSC2454Exception) prevents attempts to save a file
 * that hasn't been created or has been deleted, enhancing error handling.
 * The DemoObserver class tests all functionalities, ensuring proper event notifications,
 * file deletion, and exception handling.
 */

// Tests the functionality of the Observer pattern with file operations
public class DemoObserver {
    public static void main(String[] args) {
        Editor editor = new Editor();

        // Creating event listeners
        LogOpenListener logListener = new LogOpenListener("log.txt");
        EmailNotificationListener emailListener = new EmailNotificationListener("admin@example.com");
        DeleteFileListener deleteListener = new DeleteFileListener();

        // Subscribing listeners
        editor.events.subscribe("open", logListener);
        editor.events.subscribe("save", emailListener);
        editor.events.subscribe("delete", deleteListener);

        try {
            // Open a file (creates the file in the OS)
            editor.openFile("testFile.txt");

            // Save the file
            editor.saveFile();

            // Unsubscribe the email listener and attempt to save again
            editor.events.unsubscribe("save", emailListener);
            editor.saveFile(); // No email notification should be sent

            // Delete the file
            editor.deleteFile();

            // Try to save again after deletion (should throw an exception)
            editor.saveFile();
        } catch (COSC2454Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
