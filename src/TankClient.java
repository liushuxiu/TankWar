import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    Tank myTank = new Tank(50, 50,true, this);
    Tank enemyTank = new Tank(120, 80,false, this);
    // Missile m= new Missile(50,50, Tank.Direction.R);

    List<Missile> missiles = new ArrayList<>();


    Image offScreenImage = null;


    @Override
    public void paint(Graphics g) {

        g.setColor(Color.yellow);
        g.drawString("missiles count: " + missiles.size(), 10, 10);
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            missile.hitTank(enemyTank);
            missile.draw(g);
        }
        myTank.draw(g);
        enemyTank.draw(g);

    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, 600);
        }

        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.DARK_GRAY);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, 600);
        gOffScreen.setColor(c);
        paint(gOffScreen);

        g.drawImage(offScreenImage, 0, 0, null);
    }

    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.lauchFrame();

    }

    private void lauchFrame() {
        this.setLocation(400, 300);
        this.setSize(GAME_WIDTH, 600);
        this.setTitle("坦克大战");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setBackground(Color.green);
        this.setResizable(false);

        this.addKeyListener(new KeyMonitor());
        setVisible(true);
        new Thread(new PaintThread()).start();
    }

    private class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);

        }
    }

}