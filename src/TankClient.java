import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    Tank myTank = new Tank(50, 50,this);
    // Missile m= new Missile(50,50, Tank.Direction.R);
    Missile m = null;

    Image offScreenImage = null;


    @Override
    public void paint(Graphics g) {
        myTank.draw(g);
        if (m != null) {
            m.draw(g);
        }
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, 600);
        }

        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
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

        this.setBackground(Color.black);
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