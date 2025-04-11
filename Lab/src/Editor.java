// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// The Editor class manages file operations and notifies subscribers when actions happen
public class Editor {
    public EventManager events; // Manages event subscriptions and notifications
    private File file; // Represents the file currently opened

    public Editor() {
        this.events = new EventManager("open", "save", "delete"); // Initializes events for open, save, and delete
    }

    // Opens or creates a file and notifies observers
    public void openFile(String filePath) throws IOException {
        this.file = new File(filePath);
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists: " + file.getName());
        }
        events.notify("open", file); // Notifies observers that a file was opened
    }

    // Saves content to the file and notifies observers, throws an exception if the file does not exist
    public void saveFile() throws COSC2454Exception, IOException {
        if (this.file == null || !this.file.exists()) {
            throw new COSC2454Exception("This is a COSC2454 Error: Please open or create a file first!");
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("This is a test content.");
            System.out.println("File saved successfully.");
        }
        events.notify("save", file); // Notifies observers that a file was saved
    }

    // Deletes the file from the system and notifies observers
    public void deleteFile() {
        if (this.file != null && this.file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted: " + file.getName());
                events.notify("delete", file); // Notifies observers that the file was deleted
                this.file = null; // Clears the reference after deletion
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("No file to delete.");
        }
    }
}
