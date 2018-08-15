import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.List;

public  class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private   List<TankServer.Client> clients;
    public UdpServerHandler(List<TankServer.Client> clients) {
        this.clients=clients;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

        System.out.println(" server received a packet");


        ByteBuf buf = packet.copy().content();

        for (int i = 0; i < clients.size(); i++) {
             buf.retain();
             TankServer.Client client = clients.get(i);
//            System.err.println(client.ip);
//            System.err.println(client.udpPort);
              ctx.writeAndFlush(new DatagramPacket(buf, new InetSocketAddress(client.ip, client.udpPort)));

        }

//
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "utf-8");
//        System.out.println(body);//打印收到的信息
//        //向客户端发送消息
//        String json = "来自服务端: 南无阿弥陀佛";
////        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
//        byte[] bytes = json.getBytes("UTF-8");
//        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
//       ctx.writeAndFlush(data);//向客户端发送消息
    }
}