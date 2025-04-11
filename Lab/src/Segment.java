// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import java.awt.*;

// Segment class for the Centipede
public class Segment {
    int x, y;              // Position of the segment
    Segment next;          // Reference to the next segment

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Method to draw the segment
    public void draw(Graphics g) {
        g.setColor(Color.GREEN); // Color for the centipede segments
        g.fillRect(x, y, 15, 15); // Draw segment as a square
    }

    // a method to get the bounds for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, 15, 15);
    }
}
