import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;


public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Centipede centipede;
    private LinkedList<Point> bullets= new LinkedList<>();
    private int playerX = 400;
    private int level =1;
    private  boolean easyMode = true;
    private  boolean running= true;
    private int pWidth = 50;
    private int pHeight =35;

    private Timer timer = new Timer (200, this);

    public GamePanel(){
        setBackground(Color.BLACK);
        centipede= new Centipede(0,100,20);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }


    protected void paintComponet(Graphics g){
        super.paintComponent(g);

        //Draw centipede
        Segment current = centipede.head;
        g.setColor(Color.ORANGE);
        while (current!= null){
            g.fillOval(current.x, current.y, 15,15);
            current=current.next;
        }

        //draw player
        g.setColor(Color.CYAN);
        g.fillRect(playerX, 550, pWidth, pHeight);

        //Draw bullets
        g.setColor(Color.RED);
       // g.fillRect(b.x, b.y, 5,10);

        //draw"GAME OVER"
        Font gamefont = new Font("Times New Roman", Font.BOLD, 30);
        g.setFont(gamefont);

        if (!running){
            g.setColor(Color.RED);
            g.drawString("GAME OVER", getWidth()/2, getHeight()/2);
        }
    }

   private void checkCollions(){
        LinkedList<Point> toRemove = new LinkedList<>();

        for (Point bullet : bullets){
            Segment current= centipede.head;
            while(current != null){
                double dist = bullet.distance(current.x +10,current.y +10);

                if (dist<15){
                    if (current == centipede.head){
                        level++;
                        centipede = new Centipede(0,100,10*level);
                    } else{
                        if(easyMode){
                            centipede.split(current);

                        }else {
                            //customize hard mode
                        }
                    }
                }
            }
        }
   }





    @Override
    public void actionPerformed(ActionEvent e) {
        //centipede.move();

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent. VK_LEFT){
            //playerX
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
