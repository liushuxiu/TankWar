import io.netty.buffer.ByteBuf;

public class MissileMsg implements  Msg {

    TankClient tc;
    int msgType = Msg.TANK_MISSILE_MSG;
    private Missile m;


    public MissileMsg(Missile m){
        this.m=m;
    }

    public MissileMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(msgType);

        buf.writeInt(m.tankId);
        buf.writeInt(m.x);
        buf.writeInt(m.y);
        buf.writeInt(m.dir.ordinal());
        buf.writeBoolean(m.good);
    }

    @Override
    public void parse(ByteBuf buf) {


        int tankId=buf.readInt();
        if (tankId==tc.myTank.id) {
            return;
        }
        int x=buf.readInt();
        int y=buf.readInt();
        Dir dir =Dir.values()[buf.readInt()];
        boolean good = buf.readBoolean();

        Missile m= new Missile(tankId,x,y,good,dir,tc);
        tc.missiles.add(m);

    }
}
