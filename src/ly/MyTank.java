//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//
//public class MyTank {
//
//    public static final int XSPEED = 5;
//    public static final int YSPEED = 5;
//    private int x;
//    private int y;
//    private boolean bL = false;
//    private boolean bU = false;
//    private boolean bR = false;
//    private boolean bD = false;
//
//    private ArrayList<Blood> list=new ArrayList<>();
//
//
//    enum Direction {L, LU, U, RU, R, RD, D, LD, STOP}
//
//    ;
//
//    private Direction dir = Direction.STOP;
//    private Direction lastDir = Direction.STOP;
//
//
//    public MyTank(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public void draw(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillOval(x, y, 50, 50);
//        g.setColor(c);
//
//        for(Blood b : list){
//            b.draw(g);
//
//            b.move();
//        }
//
//        move();
//    }
//
//
//    void move() {
//        switch (dir) {
//            case L:
//                x -= XSPEED;
//                break;
//            case LU:
//                x -= XSPEED;
//                y -= YSPEED;
//                break;
//            case U:
//                y -= YSPEED;
//                break;
//            case RU:
//                x += XSPEED;
//                y -= YSPEED;
//                break;
//            case R:
//                x += XSPEED;
//                break;
//            case RD:
//                x += XSPEED;
//                y += YSPEED;
//                break;
//            case D:
//                y += YSPEED;
//                break;
//            case LD:
//                x -= XSPEED;
//                y += YSPEED;
//                break;
//            case STOP:
//                break;
//
//        }
//    }
//
//    public void keyPressed(KeyEvent e) {
//        int key = e.getKeyCode();
//
//        switch (key) {
//
//            case KeyEvent.VK_LEFT:
//                bL = true;
//                break;
//            case KeyEvent.VK_UP:
//                bU = true;
//                break;
//            case KeyEvent.VK_RIGHT:
//                bR = true;
//                break;
//            case KeyEvent.VK_DOWN:
//                bD = true;
//                break;
//            case 32 :
//               list.add(new Blood(x,y,dir)) ;
//                break;
//        }
//
//        locateDirection();
//    }
//    public void keyReleased(KeyEvent e) {
//        int key = e.getKeyCode();
//
//        switch (key) {
//
//            case KeyEvent.VK_LEFT:
//                bL = false;
//                break;
//            case KeyEvent.VK_UP:
//                bU = false;
//                break;
//            case KeyEvent.VK_RIGHT:
//                bR = false;
//                break;
//            case KeyEvent.VK_DOWN:
//                bD = false;
//                break;
//        }
//
//        locateDirection();
//    }
//    void locateDirection() {
//        if (bL && !bU && !bR && !bD) dir = Direction.L;
//        else if (bL && bU && !bR && !bD) dir = Direction.LU;
//        else if (!bL && bU && !bR && !bD) dir = Direction.U;
//        else if (!bL && bU && bR && !bD) dir = Direction.RU;
//        else if (!bL && !bU && bR && !bD) dir = Direction.R;
//        else if (!bL && !bU && bR && bD) dir = Direction.RD;
//        else if (!bL && !bU && !bR && bD) dir = Direction.D;
//        else if (bL && !bU && !bR && bD) dir = Direction.LD;
//        else if (!bL && !bU && !bR && !bD) {
//            dir = Direction.STOP;
//        }
//    }
//
//
//}
