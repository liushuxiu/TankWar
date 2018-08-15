

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {

    int id;

    public static final int XSPEED = 5;
    public static final int YSPEED = 5;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    int x;
    int y;
    private boolean bL = false;
    private boolean bU = false;
    private boolean bR = false;
    private boolean bD = false;

    boolean good;
    private boolean live = true;

    private int step = random.nextInt(12) + 3;

    private static Random random = new Random();

    TankClient tc;


    Dir dir = Dir.STOP;
    Dir ptDir = Dir.D;


    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;


    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public Tank(int x, int y, boolean good, Dir dir, TankClient tc) {
        this(x, y, good);
        this.tc = tc;
        this.dir = dir;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void draw(Graphics g) {

        if (!live) {
            if (!good) {
                tc.tanks.remove(this);
            }

            return;
        }

        Color c = g.getColor();
        if (good) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.blue);
        }
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.drawString("id:" + id, x, y - 10);


        g.setColor(Color.YELLOW);
        switch (ptDir) {
            case L:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
                break;
            case LU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
                break;
            case RU:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
                break;
            case RD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
                break;
            case D:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
                break;
            case LD:
                g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
                break;
        }
        g.setColor(c);

        move();
    }

    public Missile fire() {
        if (!live) {
            return null;
        }
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile m = new Missile(x, y, good, ptDir, this.tc);
        tc.missiles.add(m);
        return m;

    }

    void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }

        if (this.dir != Dir.STOP) {
            this.ptDir = this.dir;
        }
        if (x < 0) x = 0;
        if (y <= 30) y = 30;
        if (x + Tank.WIDTH > TankClient.GAME_HEIGHT) x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

        int count = 0;
        if (!good) {
            Dir[] dirs = Dir.values();

            if (step == 0) {
                step = random.nextInt(12) + 3;
                int randomNext = random.nextInt(dirs.length);
                dir = dirs[randomNext];
            }

            step--;
            if (random.nextInt(50) > 48) {
                this.fire();
            }


        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
//            case 32:
//                fire();
//                break;

        }

        locateDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case 32:
                fire();
                break;
        }

        locateDirection();
    }

    void locateDirection() {
        Dir oldDir = this.dir;


        if (bL && !bU && !bR && !bD) dir = Dir.L;
        else if (bL && bU && !bR && !bD) dir = Dir.LU;
        else if (!bL && bU && !bR && !bD) dir = Dir.U;
        else if (!bL && bU && bR && !bD) dir = Dir.RU;
        else if (!bL && !bU && bR && !bD) dir = Dir.R;
        else if (!bL && !bU && bR && bD) dir = Dir.RD;
        else if (!bL && !bU && !bR && bD) dir = Dir.D;
        else if (bL && !bU && !bR && bD) dir = Dir.LD;
        else if (!bL && !bU && !bR && !bD) {
            dir = Dir.STOP;
        }


        if (dir != oldDir && oldDir != Dir.STOP && dir != Dir.STOP) {
            System.err.println("dir:" + dir + "   oldDir:" + oldDir);
            TankMoveMsg msg = new TankMoveMsg(id, dir);

            tc.nc.send(tc.nc.myChannel, msg);

        }
    }


}
