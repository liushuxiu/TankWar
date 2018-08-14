import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TankServer {

    static AtomicInteger ID = new AtomicInteger(24);
    public static final int TCP_PORT=8888;
    public static final int UDP_PORT_SERVER=6666;



    class Client {

        String ip;
        int udpPort;

        Client(String ip, int udpPort) {
            this.ip = ip;
            this.udpPort = udpPort;
        }
    }

    List<Client> clients = new ArrayList<>();



    public static void main(String[] args) throws Exception {

        TankServer server = new TankServer();

        server.startUdpServer();
        server.startTcpServer(server);
    }

    private void startUdpServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bootstrap b = new Bootstrap();
                    EventLoopGroup group = new NioEventLoopGroup();
                    b.group(group)
                            .channel(NioDatagramChannel.class)
                            .handler(new UdpServerHandler(clients));

                    b.bind(UDP_PORT_SERVER).sync().channel().closeFuture().await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void startTcpServer(TankServer server) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new TcpServerInitializer(server));


            ChannelFuture channelFuture = serverBootstrap.bind(TCP_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
