// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 3/5/2025
// Observer
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Manages event listeners and triggers notifications
public class EventManager {
    Map<String, List<EventListener>> listeners = new HashMap<>();

    // Initializes event types and their corresponding listener lists
    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    // Subscribes a listener to a specific event
    public void subscribe(String eventType, EventListener listener) {
        listeners.get(eventType).add(listener);
    }

    // Unsubscribes a listener from a specific event
    public void unsubscribe(String eventType, EventListener listener) {
        listeners.get(eventType).remove(listener);
    }

    // Notifies all listeners subscribed to a specific event
    public void notify(String eventType, File file) {
        for (EventListener listener : listeners.get(eventType)) {
            listener.update(eventType, file);
        }
    }
}
