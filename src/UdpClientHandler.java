import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf buf = packet.copy().content();

        parse(buf);

        System.out.println("我进入了udp  client handler");
        String body = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(body);
        System.out.println(packet.sender().getAddress()+":"+packet.sender().getPort());
    }

    private void parse(ByteBuf buf) {
        int id=buf.readInt();
        int x=buf.readInt();
        int y=buf.readInt();
        Dir dir =Dir.values()[buf.readInt()];
        boolean good = buf.readBoolean();

        System.err.println("id:"+id+"  x:"+x+" y:"+y+" dir: "+dir+" good: "+good);
    }


}