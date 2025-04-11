// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import javax.swing.*;

public class CentiGame {
    public static void main(String[] args) {
        // Use the SwingUtilities to ensure that the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create a new JFrame for the game
            JFrame frame = new JFrame("Centipede Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
            frame.setSize(800, 600); // Set the initial size of the window

            // Add the game panel to the frame, which will contain the game logic and rendering
            frame.add(new GamePanel());

            // Prevent resizing the window to maintain a consistent game experience
            frame.setResizable(false);

            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
