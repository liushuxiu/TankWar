import com.sun.corba.se.spi.ior.Writeable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private TankClient tc;
    public UdpClientHandler(TankClient tc) {
        this.tc=tc;
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf buf = packet.copy().content();

        int msgType=buf.readInt();

        Msg msg=null;
        switch (msgType){
            case Msg.TANK_NEW_MSG:
//                TankNewMsg myTankMsg = new TankNewMsg(tc.myTank);
//                ByteBuf data= Unpooled.directBuffer();
//
//                myTankMsg.write(data);
//                ctx.writeAndFlush(new DatagramPacket(data,packet.sender()));
                msg=new TankNewMsg(tc);
                msg.parse(buf);
                break;
            case Msg.TANK_MOVE_MSG:
                msg=new TankMoveMsg(tc);
                msg.parse(buf);
                break;

            case Msg.TANK_MISSILE_MSG:
                msg = new MissileMsg(tc);
                msg.parse(buf);
                break;



        }

      //  System.out.println("我进入了udp  client handler");
   //     String body = packet.content().toString(CharsetUtil.UTF_8);
       // System.out.println(body);
     //   System.out.println(packet.sender().getAddress()+":"+packet.sender().getPort());
    }




}