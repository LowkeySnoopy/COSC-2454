// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
import java.io.File;

// Defines the Observer interface that all event listeners must implement
public interface EventListener {
        void update(String eventType, File file);
}

