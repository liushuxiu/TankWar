import java.awt.*;

public class Missile {
    public static final int XSPEED = 50;
    public static final int YSPEED = 50;
    public static final int WIDTH=10;
    public static final int HEIGHT=10;
    int x,y;
    Tank.Direction dir;

    public Missile(int x, int y, Tank.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void draw(Graphics g){
        Color c= g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        move();
    }

    private void move() {
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
    }
}
