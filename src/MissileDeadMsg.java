import io.netty.buffer.ByteBuf;

public class MissileDeadMsg implements  Msg {

    int msgType=Msg.MISSILE_DEAD_MSG;
    int tankId;
    int id;

    TankClient tc;
    public MissileDeadMsg(TankClient tc) {
        this.tc=tc;
    }

    public MissileDeadMsg(int tankId, int id) {
        this.tankId = tankId;
        this.id = id;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(msgType);
        buf.writeInt(tankId);
        buf.writeInt(id);

    }

    @Override
    public void parse(ByteBuf buf) {

        int tankId=buf.readInt();
//        if (tc.myTank.id==tankId){
//            return;
//        }

        int id =buf.readInt();
        for (int i = 0; i < tc.missiles.size(); i++) {
            Missile m =tc.missiles.get(i);
            if (m.tankId==tankId&&m.id==id){
                m.live=false;
                tc.explodes.add(new Explode(m.x, m.y, tc));
                break;
            }
        }

    }
}
