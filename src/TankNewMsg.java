import io.netty.buffer.ByteBuf;
import javafx.scene.control.Tab;

import java.util.ArrayList;

public class TankNewMsg implements Msg {


    int msgType = Msg.TANK_NEW_MSG;
    TankClient tc;
    Tank tank;

    public TankNewMsg(Tank tank) {
        this.tank = tank;
    }

    public TankNewMsg(TankClient tc) {
        this.tc = tc;
    }


    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(msgType);
        buf.writeInt(tank.id);
        buf.writeInt(tank.x);
        buf.writeInt(tank.y);
        buf.writeInt(tank.dir.ordinal());
        buf.writeBoolean(tank.good);
    }

    @Override
    public void parse(ByteBuf buf) {
        int id = buf.readInt();



        if (tc.myTank.id == id) {
            return;
        }



        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean good = buf.readBoolean();

        boolean exist = false;
        for (int i = 0; i < tc.tanks.size(); i++) {
            Tank t = tc.tanks.get(i);
            if (t.id == id) {
                exist = true;
                break;
            }

        }

        if (!exist) {
            TankNewMsg myTankMsg = new TankNewMsg(tc.myTank);
            tc.nc.send(tc.nc.myChannel,myTankMsg);
            Tank t = new Tank(x, y, good, dir, tc);
            t.id = id;
            tc.tanks.add(t);
        }


        //   System.err.println("id:"+id+"  x:"+x+" y:"+y+" dir: "+dir+" good: "+good);
    }
}
