import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个Handler，需要继承netty规定好的ChannelInboundHandlerAdapter规范
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取数据事件（可以读取客户端发送的消息）
    // ChannelHandlerContext，上下文对象，含有管道pipeline，通道channel，地址
    // 管道做业务处理，通道负责读写
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server ctx = " + ctx);

        // 性能比NIO的ByteBuffer更高
        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送的消息是： " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址： " + ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // 将数据写入到缓存，并刷新到channel
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端～", CharsetUtil.UTF_8));
    }

    // 处理异常，一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}






