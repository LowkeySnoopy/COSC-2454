// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/19/2025
// Snake Game
/*
 * This is a simple 2D action game inspired by the classic Centipede.
 * The game features multiple enemies, including a centipede, a boss,
 * and various power-ups and obstacles. Players control a spaceship
 * represented by a blue rectangle and must shoot bullets to destroy
 * segments of the centipede and the boss. The player earns points for
 * every segment destroyed, and lives are lost if they are hit by enemy
 * bullets or brown rectangle obstacles that reduce their score and lives.
 * The objective is to clear the centipede and defeat the boss to win
 * the game.
 * Key features include:
 * - Player movement using left and right arrow keys.
 * - Shooting mechanic with the spacebar.
 * Additions to the game include:
 * - The player earns points for every segment destroyed, and blue square collected blue square
 * - Enhancing the centipedes' behavior making them aggressive by being able to shoot back
 * - A Boss fight that progressively gets harder by making him move faster and shoot faster has his health goes down
 * - The boss has a health bar and a player has lives
 * - Boss mechanics that trigger new centipedes to spawn when damaged.
 * - A brown rectangle obstacle that decreases the player's lives and score if hit.
 * - A random cyan square power-up that spawns in that increases the player's lives.
 * - Improving the user interface.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    private enum GameState { MENU, PLAYING, WIN, GAME_OVER }
    private GameState state = GameState.MENU;  // Initial game state is MENU

    private Centipede centipede; // The centipede object
    private Boss boss; // The boss object
    private LinkedList<Point> bullets = new LinkedList<>(); // List to store player bullets
    private LinkedList<Point> enemyBullets = new LinkedList<>(); // List to store enemy bullets
    private int playerX = 400; // Player's horizontal position
    private int score = 0; // Player's score
    private int lives = 3; // Player's remaining lives
    private boolean running = true; // Game running flag
    private int pWidth = 50, pHeight = 35; // Player dimensions
    private Random random = new Random(); // Random number generator
    private PowerUp powerUp; // Power-up object
    private Timer powerUpTimer; // Timer for spawning power-ups
    private BrownRectangle brownRectangle; // Brown rectangle object
    private Timer brownRectangleTimer; // Timer for spawning brown rectangles
    private Timer timer = new Timer(20, this); // Main game timer

    public GamePanel() {
        setBackground(Color.BLACK); // Set background color to black
        addKeyListener(this); // Add key listener for keyboard input
        addMouseListener(this); // Add mouse listener for mouse events
        setFocusable(true); // Allow focus on this panel

        // Timer to spawn brown rectangles every 5 seconds
        brownRectangleTimer = new Timer(5000, e -> spawnBrownRectangle());
    }

    private void startGame() {
        centipede = new Centipede(0, 100, 20); // Initialize centipede
        resetGameVariables(); // Reset game variables
        state = GameState.PLAYING; // Change game state to PLAYING
        timer.start(); // Start the main game timer

        // Timer to spawn power-ups every minute
        powerUpTimer = new Timer(20000, e -> spawnPowerUp());
        powerUpTimer.start(); // Start the power-up timer
    }

    private void resetGameVariables() {
        bullets.clear(); // Clear the bullets list
        enemyBullets.clear(); // Clear the enemy bullets list
        playerX = 400; // Reset player's position
        score = 0; // Reset score
        lives = 3; // Reset lives
        running = true; // Set running flag to true
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass's paint method
        switch (state) {
            case MENU:
                drawMenu(g); // Draw the menu
                break;
            case PLAYING:
                drawGame(g); // Draw the game
                break;
            case WIN:
                drawEndScreen(g, "YOU WIN!"); // Draw win screen
                break;
            case GAME_OVER:
                drawEndScreen(g, "GAME OVER"); // Draw game over screen
                break;
        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.WHITE); // Set color to white
        g.setFont(new Font("Arial", Font.BOLD, 40)); // Set font for title
        g.drawString("CENTIPEDE GAME", getWidth() / 2 - 150, 100); // Draw game title
        g.setColor(Color.GREEN); // Set color to green for the button
        g.fillRect(getWidth() / 2 - 50, 200, 100, 50); // Draw play button
        g.setColor(Color.BLACK); // Set color to black for button text
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Set font for button text
        g.drawString("PLAY", getWidth() / 2 - 25, 230); // Draw button text
    }

    private void drawGame(Graphics g) {
        if (centipede != null) {
            drawCentipede(g); // Draw the centipede if it exists
        } else if (boss == null) {
            boss = new Boss(300, 50, getWidth()); // Create boss if centipede is gone
        }

        drawPlayer(g); // Draw the player
        drawBullets(g); // Draw bullets
        drawScoreAndLives(g); // Draw score and lives

        if (boss != null && boss.isAlive()) {
            boss.draw(g); // Draw boss if it is alive
        }

        // Draw power-up if it's alive
        if (powerUp != null && powerUp.isAlive()) {
            powerUp.draw(g);
        }

        // Draw brown rectangle if it's alive
        if (brownRectangle != null && brownRectangle.isAlive()) {
            brownRectangle.draw(g);
        }
    }

    private void drawCentipede(Graphics g) {
        g.setColor(Color.GREEN); // Set color to green for the centipede
        Segment current = centipede.head; // Start from the head of the centipede
        while (current != null) {
            g.fillOval(current.x, current.y, 15, 15); // Draw centipede segment
            current = current.next; // Move to the next segment
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.BLUE); // Set color to blue for the player
        g.fillRect(playerX, 550, pWidth, pHeight); // Draw player rectangle
    }

    private void drawBullets(Graphics g) {
        g.setColor(Color.RED); // Set color to red for player bullets
        for (Point bullet : bullets) {
            g.fillRect(bullet.x, bullet.y, 5, 10); // draw bullet
        }
        g.setColor(Color.YELLOW); // Set color to yellow for enemy bullets
        for (Point enemyBullet : enemyBullets) {
            g.fillRect(enemyBullet.x, enemyBullet.y, 5, 10); // draw bullet
        }
    }

    private void drawScoreAndLives(Graphics g) {
        g.setColor(Color.WHITE); // Set color to white for score and lives
        g.setFont(new Font("Arial", Font.BOLD, 20)); //Font
        g.drawString("Score: " + score, 10, 20);  // Draw score
        g.drawString("Lives: " + lives, getWidth() - 100, 20);  // Draw lives
    }

    private void drawEndScreen(Graphics g, String message) {
        g.setColor(Color.WHITE); // Set color to white for end screen message
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(message, getWidth() / 2 - 100, getHeight() / 2);
             }

    private void checkCollisions() { // checks collisions for bullets,boss,and win condition
        checkBulletCollisions();
        checkBossCollisions();
        checkWinCondition();
    }

    private void checkBulletCollisions() {
        // Check if centipede is null
        if (centipede == null) return;

        LinkedList<Point> bulletsToRemove = new LinkedList<>();// List to keep track of bullets to remove

        for (Point bullet : bullets) {
            Segment current = centipede.head; // Start from the head of the centipede
            // Check if bullet hits centipede segment
            while (current != null) {
                if (bullet.distance(current.x + 10, current.y + 10) < 15) {
                    score += 25; // Increase score for hitting centipede
                    centipede.split(current);  // Split the centipede at the hit segment
                    bulletsToRemove.add(bullet); // Mark bullet for removal
                    break;
                }
                current = current.next; // Move to the next segment
            }
        }

        bullets.removeAll(bulletsToRemove); // Remove bullets that hit centipede
        // If no centipede segments remain, spawn the boss
        if (centipede.head == null) {
            centipede = null;// Set centipede to null
            if (boss == null) { // Ensure boss is created only once
                boss = new Boss(300, 50, getWidth());  // Create boss
                brownRectangleTimer.start(); // Start the timer when the boss spawns
            }
        }
    }

    private void checkBossCollisions() {
        if (boss != null && boss.isAlive()) {
            LinkedList<Point> bulletsToRemove = new LinkedList<>();
            for (Point bullet : bullets) {
                // Check if bullet hits boss
                if (boss.getBounds().intersects(new Rectangle(bullet.x, bullet.y, 5, 10))) {
                    boss.takeDamage(10); // Damage the boss
                    bulletsToRemove.add(bullet);

                    // Spawn a new centipede when boss health reaches 50%
                    if (boss.health == boss.maxHealth / 2 && centipede == null) {
                        centipede = new Centipede(0, 100, 20);
                    }

                    // Spawn a new centipede when boss health reaches 25%
                    if (boss.health <= boss.maxHealth * 0.25 && centipede == null) {
                        centipede = new Centipede(0, 100, 20);
                    }
                }
            }
            bullets.removeAll(bulletsToRemove);// Remove bullets that hit boss
        }
    }


    private void checkWinCondition() { //check for win
        if (centipede == null && boss != null && !boss.isAlive()) {
            state = GameState.WIN;
            timer.stop();
        }
    }

    private void spawnBrownRectangle() { //spawns in obstacle
        int randomX = random.nextInt(getWidth() - 20); // Random x position
        brownRectangle = new BrownRectangle(randomX, 0); // Spawn at the top
    }

    private void checkShipCollision() {
        Rectangle shipRect = new Rectangle(playerX, 550, pWidth, pHeight);
        LinkedList<Point> bulletsToRemove = new LinkedList<>();

        // Check if an enemy bullet hits the player
        for (Point enemyBullet : enemyBullets) {
            if (shipRect.intersects(new Rectangle(enemyBullet.x, enemyBullet.y, 5, 10))) {
                bulletsToRemove.add(enemyBullet);
                loseLife(); // Lose a life
            }
        }
        enemyBullets.removeAll(bulletsToRemove);
    }

    private void checkPowerUpCollision() {
        if (powerUp != null && powerUp.isAlive()) {
            Rectangle playerRect = new Rectangle(playerX, 550, pWidth, pHeight);
            if (playerRect.intersects(powerUp.getBounds())) {
                powerUp.collect(); // Collect the power-up
                lives++; // Gain an extra life
                score += 50; // add score for collecting the power-up
                powerUp = null; // Remove the power-up after collection
            }
        }
    }

    private void checkBrownRectangleCollision() {
        if (brownRectangle != null && brownRectangle.isAlive()) {
            Rectangle playerRect = new Rectangle(playerX, 550, pWidth, pHeight);
            if (playerRect.intersects(brownRectangle.getBounds())) {
                brownRectangle.collect(); // Collect the brown rectangle
                // Handle the effect of collecting the brown rectangle here
                lives--; // Decrease the player's life
                score -= 50; //  decrease score for collecting the brown rectangle
                brownRectangle = null; // Remove the brown rectangle after collection
            }
        }
    }


    private void loseLife() { //lose life logic
        lives--;
        if (lives <= 0) {
            state = GameState.GAME_OVER;
            timer.stop();
        }
    }

    private void enemyShoot() {
        if (boss != null && boss.isAlive()) {
            boss.shoot(enemyBullets); // Pass the enemyBullets list to the boss
        }

        // Existing centipede shooting logic
        if (random.nextInt(100) < 2 && centipede != null) { // Check if centipede is not null
            Segment current = centipede.head;
            while (current != null) {
                if (random.nextInt(5) == 0) {
                    enemyBullets.add(new Point(current.x + 5, current.y + 10));
                }
                current = current.next;
            }
        }
    }

    private void spawnPowerUp() { //spawns a power up
        int randomX = random.nextInt(getWidth() - 20); // Random x position
        powerUp = new PowerUp(randomX, 0); // Spawn at the top
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (state != GameState.PLAYING) return;

        // Update centipede movement and collision checking
        if (centipede != null) {
            centipede.move();
            centipede.checkAndTurn(getWidth(), getHeight());
        }

        // Update boss movement and shooting
        if (boss != null && boss.isAlive()) {
            boss.update(enemyBullets); // Update boss's movement and shooting
        }

        // Move player bullets up and enemy bullets down
        for (Point bullet : bullets) bullet.y -= 15;
        for (Point enemyBullet : enemyBullets) enemyBullet.y += 10;
        enemyBullets.removeIf(enemyBullet -> enemyBullet.y > getHeight());

        // Update power-up
        if (powerUp != null && powerUp.isAlive()) {
            powerUp.update(); // Update power-up position
        }

        // Update brown rectangle
        if (brownRectangle != null && brownRectangle.isAlive()) {
            brownRectangle.update(); // Update brown rectangle position
        }

        // Check for collisions
        checkCollisions();
        checkShipCollision();
        checkPowerUpCollision(); // Check for power-up collision
        checkBrownRectangleCollision(); // Check for brown rectangle collision
        enemyShoot();
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (state != GameState.PLAYING) return;

        switch (e.getKeyCode()) { // move player to the left
            case KeyEvent.VK_LEFT:
                playerX = Math.max(0, playerX - 50);
                break;
            case KeyEvent.VK_RIGHT: //move player to the right
                playerX = Math.min(getWidth() - pWidth, playerX + 50);
                break;
            case KeyEvent.VK_SPACE: //shoot if spacebar is hit
                fireBullets();
                break;
        }
    }

    private void fireBullets() {
        bullets.add(new Point(playerX + pWidth / 2, 550)); // Fire one bullet at a time
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (state == GameState.MENU) {
            startGame();
        }
        if (state == GameState.GAME_OVER || state == GameState.WIN) {
            resetGameVariables();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
