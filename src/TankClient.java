
import client.NetClient;

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
    public static final int TCP_PORT = 8888;
    public static final String IP="127.0.0.1";


    NetClient nc = new NetClient();

    Tank myTank = new Tank(50, 50,true, Dir.STOP, this);
//    Tank enemyTank = new Tank(120, 80,false, this);
    // Missile m= new Missile(50,50, Tank.Direction.R);

    List<Tank> tanks = new ArrayList<>();

    List<Missile> missiles = new ArrayList<>();

    List<Explode> explodes=new ArrayList<>();
//    Explode e = new Explode(70,70,this );

    Image offScreenImage = null;


    @Override
    public void paint(Graphics g) {

        g.setColor(Color.yellow);
        g.drawString("missiles count: " + missiles.size(), 10, 10);
        g.drawString("explodes count: " + explodes.size(), 10, 25);
        g.drawString("tanks count: " + tanks.size(), 10, 40);
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
//            missile.hitTank(enemyTank);
            missile.hitTanks(tanks);
            missile.hitTank(myTank);
            missile.draw(g);
        }
        for (int i = 0; i < explodes.size(); i++) {
            Explode e =explodes.get(i);
            e.draw(g);

        }
        myTank.draw(g);

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            t.draw(g);

        }
//        enemyTank.draw(g);

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

//        for (int i = 0; i < 10; i++) {
//            tanks.add(new Tank(50+40*(i+1),50,false, Dir.D,this));
//        }

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

        nc.start(IP,TCP_PORT);
    }

    private class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
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