// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/3/2025
// Falling Sanding
/* This Java program simulates a falling sand physics simulation using double buffering for smooth rendering.
It features sand particles that fall downward if space is available or sliding diagonally if blocked.
Users can interact with the simulation using the mouse—left-click to add sand, right-click to remove it,
and drag to modify multiple cells. Keyboard controls allow clearing the grid ("R") which stands for reset,
disabling physics ("D") which allows the user to draw, and re-enabling the falling physics by clicking ("F") which stands for falling.
The program continuously updates in a game loop, processing user input, updating sand positions, and repainting the display to reflect changes.
 */


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class FallingSandDoubleBuffering extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable {

    // Panel dimensions
    private final int WIDTH = 640;
    private final int HEIGHT = 240;
    private final int SIZE = 2;

    // Sand grid (each cell is true if there is sand, false if empty)
    private final boolean[][] sand = new boolean[HEIGHT][WIDTH];
    private boolean active = false;

    private int x;
    private int y;

    // Create an offscreen image for double buffering
    public Image offscreen;

    // Get a graphics object to draw onto the offscreen image
    public Graphics og;

    // Flag to control whether falling physics are enabled or not
    private boolean fallingEnabled = true;

    // Constructor: Set up the panel
    public FallingSandDoubleBuffering() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);  // Register mouse listener
        addMouseMotionListener(this);  // Register mouse motion listener
        addKeyListener(this);  // Register key listener
        setFocusable(true);  // Make sure the panel is focusable
        requestFocusInWindow();  // Request focus to receive key events
    }

    private int mouseButton = -1;

    // Mouse event handling: for adding/removing sand
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        this.active = true;
        this.mouseButton = e.getButton();
    }

    // Mouse drag event handling: update sand based on mouse actions
    public void mouseDragged(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();

        if (this.mouseButton == MouseEvent.BUTTON1) { // Left-click: Add sand
            if (this.y >= 0 && this.y < HEIGHT && this.x >= 0 && this.x < WIDTH) {
                this.sand[this.y][this.x] = true;
            }
        } else if (this.mouseButton == MouseEvent.BUTTON3) { // Right-click: Remove sand
            if (this.y >= 0 && this.y < HEIGHT && this.x >= 0 && this.x < WIDTH) {
                this.sand[this.y][this.x] = false;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        this.active = false;
        this.mouseButton = -1;
    }

    // Other unused mouse events
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    // Key event handling: for clearing sand and toggling physics
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            // Clear sand when "R" is pressed
            clearSand();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            // Disable falling physics when "D" is pressed
            fallingEnabled = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            // Enable falling physics when "F" is pressed
            fallingEnabled = true;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    // Method to clear all sand (reset grid to false)
    private void clearSand() {
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                sand[r][c] = false;
            }
        }
    }

    // Method to draw the sand and background
    public void paint(Graphics g) {
        g.drawImage(offscreen, 0, 0, this);
    }

    // Method to handle the drawing logic of the sand particles
    public void draw() {
        // Create an offscreen image for double buffering
        offscreen = createImage(this.WIDTH, this.HEIGHT);
        og = offscreen.getGraphics();

        // Fill the background with dark gray color
        og.setColor(Color.DARK_GRAY);
        og.fillRect(0, 0, this.WIDTH, this.HEIGHT);

        // Draw each sand particle
        for (int r = 0; r < this.HEIGHT; r++) {
            for (int c = 0; c < this.WIDTH; c++) {
                if (this.sand[r][c]) {
                    // Calculate color based on position for some variation
                    float red = c / (float) this.WIDTH;
                    float green = r / (float) this.HEIGHT;
                    float yellow = .5f;
                    og.setColor(new Color(red, green, yellow));
                    og.fillRect(c, r, this.SIZE, this.SIZE);
                }
            }
        }
    }

    // The main game loop: runs continuously and handles the physics and drawing
    public void run() {
        boolean running = true;
        while (running) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add or remove sand while the mouse button is pressed
            if (this.active) {
                if (this.mouseButton == MouseEvent.BUTTON1) { // Left-click: Add sand
                    if (this.y >= 0 && this.y < HEIGHT && this.x >= 0 && this.x < WIDTH) {
                        this.sand[this.y][this.x] = true;
                    }
                } else if (this.mouseButton == MouseEvent.BUTTON3) { // Right-click: Remove sand
                    if (this.y >= 0 && this.y < HEIGHT && this.x >= 0 && this.x < WIDTH) {
                        this.sand[this.y][this.x] = false;
                    }
                }
            }

            // Falling Physics (only if fallingEnabled is true)
            if (fallingEnabled) {
                // Loop through the sand particles starting from the bottom
                for (int r = HEIGHT - 2; r >= 0; r--) {
                    for (int c = 1; c < WIDTH - 1; c++) {

                        // Skip if no sand in this position
                        if (!this.sand[r][c]) {
                            continue;
                        }

                        // Try to move down
                        if (!this.sand[r + 1][c]) {
                            this.sand[r][c] = false;
                            this.sand[r + 1][c] = true;
                        }

                        // Try to move down-left
                        else if (!this.sand[r + 1][c - 1]) {
                            this.sand[r][c] = false;
                            this.sand[r + 1][c - 1] = true;
                        }

                        // Try to move down-right
                        else if (!this.sand[r + 1][c + 1]) {
                            this.sand[r][c] = false;
                            this.sand[r + 1][c + 1] = true;
                        }
                    }
                }
            }

            // Draw the updated state of the sand
            draw();
            repaint();
        }
    }
}
