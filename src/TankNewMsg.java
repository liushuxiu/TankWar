import io.netty.buffer.ByteBuf;
import javafx.scene.control.Tab;

public class TankNewMsg {

    Tank tank;
    public TankNewMsg(Tank tank){
        this.tank=tank;
    }


    public void write(ByteBuf buf) {
        buf.writeInt(tank.id);
        buf.writeInt(tank.x);
        buf.writeInt(tank.y);
        buf.writeInt(tank.dir.ordinal());
        buf.writeBoolean(tank.good);
    }
}
