import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

public class FallingSand extends Canvas implements MouseListener, MouseMotionListener {

    // Dimensions of the canvas
    private final int WIDTH = 640; // Width of the canvas in pixels
    private final int HEIGHT = 240; // Height of the canvas in pixels
    private final int SIZE = 2; // Size of each sand particle (square)

    // 2D boolean array representing sand particles
    private final boolean[][] SAND = new boolean[HEIGHT][WIDTH];

    private boolean active = false; // Tracks whether the mouse is actively pressing down
    private int x, y; // Current mouse position (x, y)

    // Constructor to initialize the canvas and register mouse listeners
    public FallingSand() {
        this.setSize(WIDTH, HEIGHT);

        // Add listeners for mouse events
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    // Called whenever the mouse is pressed
    public void mousePressed(MouseEvent e) {
        this.x = e.getX(); // Get x-coordinate of mouse
        this.y = e.getY(); // Get y-coordinate of mouse
        this.active = true; // Mark the mouse as active

    }

    // Called whenever the mouse is dragged (while holding down)
    public void mouseDragged(MouseEvent e) {
        this.x = e.getX(); // Update x-coordinate
        this.y = e.getY(); // Update y-coordinate
    }

    // Called whenever the mouse button is released
    public void mouseReleased(MouseEvent e) {
        this.active = false; // Deactivate mouse
    }

    // Unused mouse event methods
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    // Graphics rendering method
    public void paint(Graphics g) {
        // Draw the background as a dark gray rectangle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.WIDTH, this.HEIGHT);

        // Draw the sand particles
        g.setColor(Color.CYAN); // Sand color
        for (int r = 0; r < this.HEIGHT; r++) {
            for (int c = 0; c < this.WIDTH; c++) {
                if (this.SAND[r][c]) {
                    g.fillRect(c, r, this.SIZE, this.SIZE); // Draw a particle
                }
            }
        }
    }

    // Main game loop for simulating sand behavior
    public void run() {
        boolean running = true; // Control flag for the simulation
        while (running) {

            try {
                Thread.sleep(1); // Add a slight delay for smooth animation
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add sand particles where the mouse is pressed
            if (this.active) {
                this.SAND[this.y][this.x] = true;
            }

            // Simulate sand falling down
            for (int r = this.HEIGHT - 2; r >= 0; r--) { // Start from the second-to-last row
                for (int c = 1; c < this.WIDTH - 1; c++) { // Skip the edges

                    // If there is no sand at the current point, skip it
                    if (!this.SAND[r][c]) {
                        continue;
                    }

                    // Check if the space below is empty
                    if (!this.SAND[r + 1][c]) {
                        this.SAND[r][c] = false; // Remove sand from current position
                        this.SAND[r + 1][c] = true; // Move sand one row down
                    }
                    // Check if the bottom-left diagonal is empty
                    else if (!this.SAND[r + 1][c - 1]) {
                        this.SAND[r][c] = false;
                        this.SAND[r + 1][c - 1] = true;
                    }
                    // Check if the bottom-right diagonal is empty
                    else if (!this.SAND[r + 1][c + 1]) {
                        this.SAND[r][c] = false;
                        this.SAND[r + 1][c + 1] = true;
                    }
                }
            }

            // Redraw the canvas to reflect changes
            repaint();
        }
    }
}
