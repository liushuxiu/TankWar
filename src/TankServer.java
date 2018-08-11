import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TankServer {

    public static int ID = 100;


    public static final int TCP_PORT = 8888;
    public static final int UDP_PORT = 6666;

    List<Client> clients = new ArrayList<>();

    public void start() {
        new Thread(new UDPThread()).start();

        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String IP = s.getInetAddress().getHostAddress();
                int udpPort = dis.readInt();
                Client c = new Client(IP, udpPort);
                clients.add(c);

                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID++);

                System.out.println("A Client Connect : Addr = " + s.getInetAddress() + " : " + s.getPort() + "----udp port :" + udpPort);
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        new TankServer().start();

    }

    private class UDPThread implements Runnable {

        byte [] buf=new byte[1024];

        @Override
        public void run() {
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }

            System.out.println("UDP thread started at port :"+UDP_PORT);
            while (ds != null) {

                DatagramPacket dp = new DatagramPacket(buf,buf.length);
                try {
                    ds.receive(dp);
                    for (int i = 0; i < clients.size(); i++) {
                        Client c=clients.get(i);
                        String ip=c.IP;
                        dp.setSocketAddress(new InetSocketAddress(ip,c.udpPort));
                        ds.send(dp);

                    }
                    System.out.println("a packet received");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class Client {
        String IP;
        int udpPort;


        public Client(String IP, int udpPort) {
            this.IP = IP;
            this.udpPort = udpPort;
        }
    }
}
