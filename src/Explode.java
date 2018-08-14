
import java.awt.*;

public class Explode {
    int x;
    int y;

    private TankClient tc;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    private  boolean live =true;
    int [] diamter={4,7,12,18,26,32,49,30,14,6};
    int step=0;

    public void draw (Graphics g){
        if (!live) {
            tc.explodes.remove(this);
            return;
        }

        if (step==diamter.length){
            live=false;
            step=0;
            return;

        }
        Color c =g.getColor();
        g.setColor(Color.yellow);
        g.fillOval(x,y,diamter[step],diamter[step]);
        g.setColor(c);
        step++;
    }
}

