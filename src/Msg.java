import io.netty.buffer.ByteBuf;

public interface Msg {

    public static final int TANK_NEW_MSG=1;
    public static final int TANK_MOVE_MSG=2;

    public void write(ByteBuf buf);

    public void parse(ByteBuf buf);
}
