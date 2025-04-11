// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Boss {
    int x, y;               // Boss position
    int width, height;      // Boss size
    int dx;                 // Movement speed
    int health, maxHealth;  // Health attributes
    int screenWidth;        // Screen width for boundary check
    int bulletCooldown;     // Cooldown timer for shooting
    int bulletSize;         // Size of the bullets
    int bulletSpeed;        // Speed of the bullets

    Random random; // Random object for shooting

    public Boss(int startX, int startY, int screenWidth) {
        this.x = startX;
        this.y = startY;
        this.width = 80;  // Boss width
        this.height = 50; // Boss height
        this.dx = 5;      // Speed
        this.screenWidth = screenWidth; // Initialize screen width

        this.maxHealth = 500; // Boss max health
        this.health = maxHealth;

        this.bulletCooldown = 0; // Start with zero cooldown
        this.bulletSize = 50; // Default bullet size
        this.bulletSpeed = 5; // Default bullet speed
        random = new Random(); // Initialize random object
    }

    public void move() {
        // Move boss left and right
        x += dx;

        // Check for boundary collisions
        if (x <= 0) {
            dx = 5; // Change direction to right
            x = 0; // Prevent moving out of bounds
        } else if (x + width >= screenWidth) {
            dx = -5; // Change direction to left
            x = screenWidth - width; // Prevent moving out of bounds
        }
    }

    public void shoot(LinkedList<Point> enemyBullets) {
        if (bulletCooldown <= 0) {
            // Randomly decide how many bullets to shoot (between 1 and 5)
            int numBullets = random.nextInt(5) + 1; // 1 to 5 bullets

            for (int i = 0; i < numBullets; i++) {
                // Randomly decide the shooting direction (left, right, or down)
                int shootDirection = random.nextInt(3); // 0: left, 1: right, 2: down

                if (shootDirection == 0) {
                    enemyBullets.add(new Point(x, y + height / 2)); // Shoot left
                } else if (shootDirection == 1) {
                    enemyBullets.add(new Point(x + width - bulletSize, y + height / 2)); // Shoot right
                } else {
                    enemyBullets.add(new Point(x + width / 2 - bulletSize / 2, y + height)); // Shoot down
                }
            }

            bulletCooldown = (health > maxHealth / 2) ? 30 : 15; // Reset cooldown based on health
        }
    }

    public void update(LinkedList<Point> enemyBullets) {
        move(); // Update boss position
        shoot(enemyBullets); // Always attempt to shoot

        // Decrease bullet cooldown
        if (bulletCooldown > 0) {
            bulletCooldown--;
        }

        // Increase movement speed and bullet speed when health is below 25%
        if (health <= maxHealth * 0.25) {
            bulletSpeed = 17; // Increase bullet speed

            // Move boss left and right faster
            x += dx;

            // Ensure increased movement speed in both directions
            if (dx > 0) {
                dx = 8;  // Move right
            } else {
                dx = -8; // Move left
            }

            // Prevent moving out of bounds and reverse direction
            if (x <= 0) {
                x = 0;
                dx = 8; // Change direction to right
            } else if (x + width >= screenWidth) {
                x = screenWidth - width;
                dx = -8; // Change direction to left
            }
        }

    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0; // Prevent negative health
        }
        // Change bullet size when health drops below 50%
        if (health <= maxHealth / 2) {
            bulletSize = 100; // Increase bullet size
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if (isAlive()) {
            // Draw boss
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);

            // Draw health bar
            g.setColor(Color.BLACK);
            g.fillRect(x, y - 10, width, 8);
            g.setColor(Color.GREEN);
            g.fillRect(x, y - 10, (int) ((width * (double) health) / maxHealth), 8);
        }
    }
}
