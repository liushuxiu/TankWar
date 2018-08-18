import io.netty.buffer.ByteBuf;

public class TankMoveMsg implements Msg {

    int msgType=Msg.TANK_MOVE_MSG;

    int id;
    int x;
    int y;
    Dir dir;
    TankClient tc;

    public TankMoveMsg(int id,int x, int y, Dir dir) {
        this.id = id;
        this.x=x;
        this.y=y;
        this.dir = dir;
    }

    public TankMoveMsg(TankClient tc) {
        this.tc=tc;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(msgType);

        buf.writeInt(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(dir.ordinal());
    }

    @Override
    public void parse(ByteBuf buf) {
        int id=buf.readInt();
        if (tc.myTank.id==id){
            return;
        }
        int x=buf.readInt();
        int y=buf.readInt();
        Dir dir =Dir.values()[buf.readInt()];

        boolean exist=false;
        for (int i = 0; i < tc.tanks.size(); i++) {
            Tank t = tc.tanks.get(i);
            if (t.id==id){
                t.x=x;
                t.y=y;
                t.dir=dir;
                exist=true;
                break;
            }

        }



    }
}
