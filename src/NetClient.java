
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NetClient {

    TankClient tc;
    Channel myChannel;


    public int udpPort;

    public NetClient(TankClient tc) {
        this.tc = tc;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public NetClient() {

    }

    public void start(String ip, int tcpPort) {

        try {
            startTcpClient(ip, tcpPort);


            startUdpClient();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    boolean hasMsg = false;

    private void startUdpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup group = new NioEventLoopGroup();
                try {
                    Bootstrap b = new Bootstrap();
                    b.group(group)
                            .channel(NioDatagramChannel.class)
                            .handler(new UdpClientHandler(tc));

                    Channel ch = b.bind(udpPort).sync().channel();
                    myChannel = ch;

//                    ch.writeAndFlush(new DatagramPacket(
//                            Unpooled.copiedBuffer("来自客户端:南无本师释迦牟尼佛", CharsetUtil.UTF_8),
//                            new InetSocketAddress("127.0.0.1", 6666))).sync();

                    TankNewMsg msg = new TankNewMsg(tc.myTank);
                    send(ch,msg);
                    ch.closeFuture().await();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    group.shutdownGracefully();
                }
            }
        }).start();
    }

    public void send(Channel ch, Msg msg)  {
     //   System.out.println("我改变了方向");
        ByteBuf buf = Unpooled.directBuffer();
        msg.write(buf);
       try {
           ch.writeAndFlush(new DatagramPacket(buf, new InetSocketAddress("127.0.0.1", 6666))).sync();
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    private void startTcpClient(String ip, int TCP_PORT) throws Exception {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker).channel(NioSocketChannel.class)
                    .handler(new TcpClientInitalizer(tc));
            ChannelFuture channelFuture = bootstrap.connect(ip, TCP_PORT).sync();
//            Scanner in = new Scanner(System.in);
//            while (in.hasNext()) {
//                String a = in.nextLine();
//                channelFuture.channel().writeAndFlush(a);
//
//            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }


}
