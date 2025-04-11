// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import java.awt.*;

public class PowerUp {
    private int x, y; // Coordinates of the power-up
    private boolean isAlive; // Status of the power-up (alive or not)

    // Constructor to initialize the power-up's position and status
    public PowerUp(int x, int y) {
        this.x = x; // Set the x-coordinate
        this.y = y; // Set the y-coordinate
        this.isAlive = true; // Mark the power-up as alive
    }

    // Method to update the power-up's position
    public void update() {
        if (isAlive) { // Only update if the power-up is alive
            y += 5; // Move down the screen at a speed of 5 pixels
            if (y > 600) { // If it goes off the screen
                isAlive = false; // Mark it as not alive
            }
        }
    }

    // Method to draw the power-up on the screen
    public void draw(Graphics g) {
        if (isAlive) { // Only draw if the power-up is alive
            g.setColor(Color.CYAN); // Set the color to cyan
            g.fillRect(x, y, 20, 20); // Draw the power-up as a square (20x20 pixels)
        }
    }

    // Method to get the bounding rectangle for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20); // Return a rectangle representing the power-up's position and size
    }

    // Method to check if the power-up is alive
    public boolean isAlive() {
        return isAlive; // Return the alive status
    }

    // Method to mark the power-up as collected
    public void collect() {
        isAlive = false; // Mark the power-up as not alive (collected)
    }

    // Getters for x and y coordinates
    public int getX() {
        return x; // Return the x-coordinate
    }

    public int getY() {
        return y; // Return the y-coordinate
    }
}
