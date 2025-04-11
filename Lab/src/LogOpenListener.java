// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
import java.io.File;

// Observer that logs when a file is opened
public class LogOpenListener implements EventListener {
    private File log; // Represents the log file

    public LogOpenListener(String fileName) {
        this.log = new File(fileName); // Initializes the log file
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Logging: " + eventType + " event for file: " + file.getName());
    }
}
