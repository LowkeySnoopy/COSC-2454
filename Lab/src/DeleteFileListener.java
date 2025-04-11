// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
import java.io.File;

// Observer that listens for file deletion events
public class DeleteFileListener implements EventListener {
    @Override
    public void update(String eventType, File file) {
        System.out.println("File Deleted Notification: " + file.getName() + " has been deleted.");
    }
}
