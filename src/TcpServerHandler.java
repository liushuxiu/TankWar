import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;

public class TcpServerHandler extends SimpleChannelInboundHandler<String> {

    private TankServer server;

    public TcpServerHandler(TankServer server) {
        this.server=server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equals("close")){
            System.out.println("客户端的TCP连接已经关闭");
            ctx.close();
            return;
        }
        String address= String.valueOf(ctx.channel().remoteAddress());
         address=address.replace("/", "");
        String[] arr = address.split(":");
        String ip =arr[0];
        int udpPort=Integer.valueOf(msg);
         TankServer.Client client = server.new Client(ip, udpPort);
        server.clients.add(client);

    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(TankServer.ID.getAndAdd(1)+"");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
