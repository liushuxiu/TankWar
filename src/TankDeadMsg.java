import io.netty.buffer.ByteBuf;

public class TankDeadMsg implements  Msg {

    int id;
    int msgType=Msg.TANK_DEAD_MSG;
    TankClient tc;
    public TankDeadMsg(int id){

        this.id=id;
    }

    public TankDeadMsg(TankClient tc){
        this.tc=tc;
    }


    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(msgType);

        buf.writeInt(id);
    }

    @Override
    public void parse(ByteBuf buf) {
        int id=buf.readInt();
        if (tc.myTank.id==id){
            return;
        }

        for (int i = 0; i < tc.tanks.size(); i++) {
            Tank t = tc.tanks.get(i);
            if (t.id==id){
               t.setLive(false);
                break;
            }

        }
    }
}
