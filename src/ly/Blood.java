//import java.awt.*;
//import java.util.ArrayList;
//
//public class Blood {
//
//    public static final int XSPEED = 50;
//    public static final int YSPEED = 50;
//    private int x;
//    private int y;
//    private boolean bL = false;
//    private boolean bU = false;
//    private boolean bR = false;
//    private boolean bD = false;
//    enum BloodDirection {L, LU, U, RU, R, RD, D, LD, STOP}
//
//    ;
//    private Tank.Direction dir = Tank.Direction.STOP;
//
//    public Blood(int x, int y, Tank.Direction dir) {
//        this.x = x;
//        this.y = y;
//        this.dir = dir;
//    }
//
//
//    public void move() {
//
//            switch (dir) {
//                case L:
//                    x -= XSPEED;
//                    break;
//                case LU:
//                    x -= XSPEED;
//                    y -= YSPEED;
//                    break;
//                case U:
//                    y -= YSPEED;
//                    break;
//                case RU:
//                    x += XSPEED;
//                    y -= YSPEED;
//                    break;
//                case R:
//                    x += XSPEED;
//                    break;
//                case RD:
//                    x += XSPEED;
//                    y += YSPEED;
//                    break;
//                case D:
//                    y += YSPEED;
//                    break;
//                case LD:
//                    x -= XSPEED;
//                    y += YSPEED;
//                    break;
//                case STOP:
//                    break;
//
//            }
//
//    }
//
//    public void draw(Graphics g) {
//        Color  c = g.getColor();
//        g.setColor(Color.YELLOW);
//        g.fillOval(x, y, 5, 5);
//        g.setColor(c);
//    }
//}
