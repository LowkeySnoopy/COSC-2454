// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import java.util.Random;

public class Centipede {
    Segment head; // The head segment of the centipede

    // Direction vectors for movement
    int dx, dy; // Change in x and y coordinates for movement
    int horizontalDir; // Indicates horizontal direction: +1 for right, -1 for left
    int verticalMoveCount = 0; // Tracks how far the centipede has moved down during a turn
    int verticalLim = 20; // Number of pixels to move down each time the centipede switches rows

    // Constructor to initialize the centipede
    public Centipede(int startX, int startY, int length) {
        this.dx = 7; // Horizontal speed of the centipede
        this.dy = 0; // Vertical speed of the centipede (initially stationary)
        this.horizontalDir = 1; // Start moving to the right

        // Building the linked segments of the centipede
        head = new Segment(startX, startY); // Create the head segment
        Segment current = head; // Set the current segment to the head

        // Create the remaining segments and link them
        for (int i = 1; i < length; i++) {
            current.next = new Segment(startX - (i * 15), startY); // Create new segment to the left
            current = current.next; // Move to the newly created segment
        }
    }

    // Method to move the centipede
    public void move() {
        if (head == null) return; // Exit if there is no centipede

        // Save the previous position of the head
        int prevX = head.x;
        int prevY = head.y;

        // Move the head segment
        head.x += dx; // Update the head's x position
        head.y += dy; // Update the head's y position

        // Move the body segments
        Segment current = head.next; // Start with the segment after the head
        while (current != null) {
            int tempX = current.x; // Store the current segment's position
            int tempY = current.y;
            current.x = prevX; // Move current segment to the position of the previous segment
            current.y = prevY;
            prevX = tempX; // Update prevX and prevY for the next segment
            prevY = tempY;
            current = current.next; // Move to the next segment
        }
    }

    // Method to check boundaries and turn the centipede when necessary
    public void checkAndTurn(int panelWidth, int panelHeight) {
        if (head == null) return; // Exit if there is no centipede

        // The approximate width of the centipede based on segment size
        int segSize = 15;
        int rBound = panelWidth - segSize; // Right boundary
        int lBound = 0; // Left boundary

        // 1. If the centipede is moving horizontally (dy == 0)
        if (dy == 0) {
            // Check if the head has reached the right boundary
            if (horizontalDir == 1 && head.x >= rBound) {
                // Start moving down
                dx = 0; // Stop horizontal movement
                dy = 7; // Start moving down
                verticalMoveCount = 0; // Reset vertical move count
            }
            // Check if the head has reached the left boundary
            else if (horizontalDir == -1 && head.x <= lBound) {
                dx = 0; // Stop horizontal movement
                dy = 7; // Start moving down
                verticalMoveCount = 0; // Reset vertical move count
            }
        } else { // If the centipede is moving vertically
            verticalMoveCount += Math.abs(dy); // Increment vertical move count

            // Check if the centipede has moved down enough to turn
            if (verticalMoveCount >= verticalLim) {
                // Flip horizontal direction
                horizontalDir = -horizontalDir; // Reverse direction
                // Update horizontal speed based on direction
                dx = 7 * horizontalDir; // Set dx to 7 or -7 based on direction
                dy = 0; // Stop vertical movement
            }
        }
    }

    // Method to split the centipede at a given segment
    public void split(Segment splitPoint) {
        if (splitPoint == head) {
            head = null; // If the head is split, the centipede no longer exists
        } else {
            Segment current = head; // Start from the head
            // Find the segment just before the split point
            while (current != null && current.next != splitPoint) {
                current = current.next; // Traverse to find the segment before the split point
            }
            // If the previous segment is found, detach the split point
            if (current != null) {
                current.next = null; // Cut the linked list at the split point
            }
        }
    }
}
