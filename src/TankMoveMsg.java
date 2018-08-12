import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMsg implements Msg{
    int msgType= Msg.TANK_MOVE_MSG;
    int id;
    Dir dir;

    TankClient tc;

    public TankMoveMsg(int id, Dir dir) {
        this.id = id;
        this.dir = dir;
    }

    public TankMoveMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String ip, int udpPort) {

        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(msgType);

            dos.writeInt(id);
            dos.writeInt(dir.ordinal());
        }catch (Exception e){
            e.printStackTrace();
        }

        byte []buf =baos.toByteArray();

        DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip,udpPort));
        try {
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream dis) {
        try {
            int id =dis.readInt();
            if (id==tc.myTank.id){
                return;
            }

            Dir dir=Dir.values()[dis.readInt()];

            boolean exist= false;
            for (int i = 0; i < tc.tanks.size(); i++) {
                Tank t=tc.tanks.get(i);
                if (t.id==id){
                    t.dir=dir;
                    exist=true;
                    break;
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
