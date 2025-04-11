// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import java.awt.*;

public class BrownRectangle {
    private int x, y; // Position of the rectangle
    private final int width = 35; // Width of the rectangle
    private final int height = 85; // Height of the rectangle
    private boolean alive; // Indicates if the rectangle is still in play

    // Constructor to initialize the rectangle's position and set it as alive
    public BrownRectangle(int x, int y) {
        this.x = x; // Set the initial x position
        this.y = y; // Set the initial y position
        this.alive = true; // Set the rectangle to be alive initially
    }

    // Update method to change the state of the rectangle each frame
    public void update() {
        y += 8; // Move the rectangle downwards at a speed of 8 pixels per frame
        if (y > 600) { // Check if the rectangle has fallen past the bottom of the screen
            alive = false; // Mark the rectangle as not alive (it has left the screen)
        }
    }

    // Draw method to render the rectangle on the screen
    public void draw(Graphics g) {
        if (alive) { // Only draw if the rectangle is alive
            g.setColor(new Color(139, 69, 19)); // Set the color to brown
            g.fillRect(x, y, width, height); // Draw the rectangle at its current position
        }
    }

    // Get the bounds of the rectangle for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // Return a Rectangle object representing the bounds
    }

    // Check if the rectangle is alive
    public boolean isAlive() {
        return alive; // Return the current state of the rectangle
    }

    // Mark the rectangle as collected (not alive)
    public void collect() {
        alive = false; // Set the rectangle's alive status to false
    }
}
