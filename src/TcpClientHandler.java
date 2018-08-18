import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpClientHandler extends SimpleChannelInboundHandler<String > {
    private TankClient tc;
    private TankServer server;
    public TcpClientHandler(TankClient tc) {
        this.tc=tc;
    }

    public TcpClientHandler(TankServer server) {
        this.server=server;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client 得到服务器分配的ID :"+msg);
        int id=Integer.valueOf(msg);
        tc.myTank.id=id;
        ctx.writeAndFlush(String.valueOf(tc.nc.udpPort));

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("close");

        ctx.close();
        System.out.println("TCP连接已经关闭");
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
