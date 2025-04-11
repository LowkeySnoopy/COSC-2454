// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
import java.io.File;

// Observer that simulates sending an email notification when an event occurs
public class EmailNotificationListener implements EventListener {
    private String email;

    public EmailNotificationListener(String email) {
        this.email = email; // Stores the recipient's email address
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation on file: " + file.getName());
    }
}
