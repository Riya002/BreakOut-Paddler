import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements  ActionListener ,KeyListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    //timer
    private Timer timer;
    private int delay = 8;

    private int playerX = 310;//starting position of player X(paddle)

    private int ballposX = 120;//starting pos of ball in x,y
    private int ballposY = 350;
    //for directions of ball
    private int ballXdr = -1;
    private int ballYdr = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //Draw krenge !!
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        //map of bricks on background
        map.draw((Graphics2D) g);

        //border for top ,left and right
        //no border for below
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(687, 0, 3, 592);

        //SCORE?!
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        ((Graphics2D) g).drawString("" + score, 590, 30);

        //paddle banate h ab !!
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (totalBricks <= 0) {

            play = false;
            ballXdr = 0;
            ballYdr = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON !! scores :" + score, 260, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press ENTER to Restart", 250, 350);
        }
        if (ballposY > 570) {
            play = false;
            ballXdr = 0;
            ballYdr = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER !! scores :" + score, 190, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press ENTER to Restart", 290, 350);
        }
        g.dispose();//graphics ka kaam khtm
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
                if(playerX>=600)
                {
                    playerX=600;
                }else
                {
                    moveRight();
                }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {

            if(playerX<10)
            {
                playerX=10;
            }else
            {
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play=true;
                ballposX=120;
                ballposY=350;
                ballXdr=-1;
                ballYdr=-2;
                playerX=310;
                score=0;
                totalBricks=21;
                map=new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveRight()
    {
        play=true;
        playerX+=20;
    }
    public void moveLeft()
    {
        play=true;
        playerX-=20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            //for ball movement
            ballposX += ballXdr;
            ballposY += ballYdr;
            if (ballposX < 0)//left
            {
                ballXdr = -ballXdr;
            }
            if (ballposY < 0)//right
            {
                ballYdr = -ballYdr;
            }
            if (ballposX > 670)//top
            {
                ballXdr = -ballXdr;
            }
            //for intersection of ball and paddle
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdr = -ballYdr;
            }
            A:
            for (int i = 0; i < map.mapArray.length; i++) {
                for (int j = 0; j < map.mapArray[0].length; j++) {
                    if (map.mapArray[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickwidth = map.brickWidth;
                        int brickheight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdr = -ballXdr;
                            } else {
                                ballYdr = -ballYdr;
                            }
                            break A;
                        }
                    }
                }
            }

        }
        repaint();
    }
}
