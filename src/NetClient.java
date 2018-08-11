import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.*;

public class NetClient {

    TankClient tc ;
    DatagramSocket ds =null;

    public static int UDP_PORT_START = 2223;
    private int udpPort;

    public NetClient(TankClient tc) {
        this.tc=tc;
        udpPort = UDP_PORT_START++;
//        try (DatagramSocket datagramSocket = ds = new DatagramSocket(udpPort)) {
//        }catch (Exception e){
//            e.printStackTrace();
//        }
       try {
           ds=new DatagramSocket(udpPort);
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    public void connect(String ip, int port) {
        Socket s = null;
        try {
            s = new Socket(ip, port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);

            DataInputStream dis=new DataInputStream(s.getInputStream());
            int id =dis.readInt();
            tc.myTank.id=id;
            System.out.println("Connected to Server and Server give me a ID: "+id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                    s=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        TankNewMsg msg = new TankNewMsg(tc.myTank);
        send(msg);
        new Thread(new UDPRecvThread()).start();
    }

    public void send(TankNewMsg msg){
        msg.send(ds,"127.0.0.1",TankServer.UDP_PORT);
    }

    private class  UDPRecvThread implements Runnable{
        byte []buf =new byte[1024];
        @Override
        public void run() {

            while (ds != null) {
                System.out.println(ds.isClosed());

                System.out.println("-----------");
                DatagramPacket dp = new DatagramPacket(buf,buf.length);
                try {
                    ds.receive(dp);
                    parse(dp);

                    System.out.println("a packet received from server");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        private void parse(DatagramPacket dp) {
            ByteArrayInputStream bais =new ByteArrayInputStream(buf,0,dp.getLength());
            DataInputStream dis =new DataInputStream(bais);
           TankNewMsg msg =new TankNewMsg(tc);
           msg.parse(dis);
        }
    }
}
