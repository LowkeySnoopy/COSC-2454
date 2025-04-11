// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Obstacles {
    private ArrayList<BrownRectangle> brownRectangles;
    private Random random;

    public Obstacles() {
        brownRectangles = new ArrayList<>();
        random = new Random();
    }

    public void update() {
        // Update positions of all brown rectangles
        for (int i = 0; i < brownRectangles.size(); i++) {
            BrownRectangle brownRectangle = brownRectangles.get(i);
            brownRectangle.update();
            // Remove the rectangle if it is no longer alive
            if (!brownRectangle.isAlive()) {
                brownRectangles.remove(i);
                i--; // Adjust index after removal
            }
        }
    }

    public void spawnBrownRectangle(int width) {
        int xPosition = random.nextInt(width - 20); // Assuming 20 is the width of the rectangle
        brownRectangles.add(new BrownRectangle(xPosition, 0)); // Start falling from the top
    }

    public void draw(Graphics g) {
        for (BrownRectangle brownRectangle : brownRectangles) {
            brownRectangle.draw(g);
        }
    }

    public ArrayList<BrownRectangle> getBrownRectangles() {
        return brownRectangles;
    }
}
