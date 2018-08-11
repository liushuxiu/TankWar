import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMsg {

    Tank tank;
    TankClient tc;

    public TankNewMsg(Tank tank){
        this.tank=tank;

    }

    public TankNewMsg(TankClient tc){
        this.tc=tc;
    }
    public TankNewMsg(){

    }

    public void send(DatagramSocket ds, String IP,int udpPort) {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeInt(tank.id);
            dos.writeInt(tank.x);
            dos.writeInt(tank.y);
            dos.writeInt(tank.dir.ordinal());
            dos.writeBoolean(tank.isGood());
        }catch (Exception e){
            e.printStackTrace();
        }

        byte []buf =baos.toByteArray();

        DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP,udpPort));
        try {
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(DataInputStream dis) {

        try {
            int id =dis.readInt();
            if (id==tc.myTank.id){
                return;
            }
            int x=dis.readInt();
            int y=dis.readInt();
            Dir dir=Dir.values()[dis.readInt()];
            boolean good=dis.readBoolean();
            System.out.println("id: "+id+"---x:  "+x+"---y:"+y+"---dir:  "+dir+"--good:"+good);

            Tank t = new Tank(x, y, good, dir, tc);
            t.id=id;
            tc.tanks.add(t);


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
