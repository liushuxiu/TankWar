

import org.omg.CORBA.INTERNAL;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final int TCP_PORT = 8888;
    public static final String IP = "127.0.0.1";

    boolean connected=false;


    public NetClient nc = new NetClient(this);
    ConnDialog dialog = new ConnDialog();

    public Tank myTank = new Tank(50, 50, true, Dir.STOP, this);
//    Tank enemyTank = new Tank(120, 80,false, this);
    // Missile m= new Missile(50,50, Tank.Direction.R);

    List<Tank> tanks = new ArrayList<>();

    List<Missile> missiles = new ArrayList<>();

    List<Explode> explodes = new ArrayList<>();
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
        //    missile.hitTanks(tanks);

            //发消息
            if (missile.hitTank(myTank)){
                TankDeadMsg msg = new TankDeadMsg(myTank.id);
                nc.send(nc.myChannel,msg);

               // MissileDeadMsg deadMsg =new MissileDeadMsg(myTank.id, missile.id);
                MissileDeadMsg deadMsg =new MissileDeadMsg(missile.tankId, missile.id);
                nc.send(nc.myChannel,deadMsg);
                nc.connected=false;

                nc.removeClient(nc.tcpChannel,"amituofo"+nc.udpPort);

            }
            missile.draw(g);
        }
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
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

   //     nc.start(IP, TCP_PORT);
    }

    class ConnDialog extends Dialog {
        Button b = new Button("确定");
        TextField tfip = new TextField("127.0.0.1",12);
        TextField tftcpport = new TextField(""+TankServer.TCP_PORT,4);
        TextField tfudpport = new TextField("2222",4);

        public ConnDialog() {
            super(TankClient.this, true);
            this.setLayout(new FlowLayout());
            this.add(new Label("IP: "));
            this.add(tfip);

            this.add(new Label("Port: "));
            this.add(tftcpport);

            this.add(new Label("My UDP port: "));
            this.add(tfudpport);
            this.setLocation(300,300);


            this.add(b);

            this.pack();
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    setVisible(false);
                }
            });

            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String  ip = tfip.getText().trim();
                    int port= Integer.parseInt(tftcpport.getText().trim());
                    int udpport=Integer.parseInt(tfudpport.getText().trim());
                    nc.setUdpPort(udpport);
                    nc.start(ip, port);
                    setVisible(false);


                }
            });


        }


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

            if (connected)
                myTank.keyReleased(e);




        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_C){
                dialog.setVisible(true);
                connected=true;
            }else {
                if (connected)
                {
                    myTank.keyPressed(e);
                }

            }

        }
    }

}