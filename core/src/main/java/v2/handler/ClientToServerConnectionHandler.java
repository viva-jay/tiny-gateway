package v2.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import v2.pool.ChannelFactory;

import java.util.Objects;

public class ClientToServerConnectionHandler extends ChannelInboundHandlerAdapter {

    private final ChannelFactory outboundChannelFactory;
    private Channel outboundChannel;


    public ClientToServerConnectionHandler(ChannelFactory outboundChannelFactory) {
        this.outboundChannelFactory = outboundChannelFactory;
    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        if(Objects.isNull(outboundChannel)) {
            outboundChannel = outboundChannelFactory.getChannel(context.channel());
        }
    }

    @Override
    public void channelRead(final ChannelHandlerContext context, Object messsage) {
        if (outboundChannel.isActive()) {
            outboundChannel.writeAndFlush(messsage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    context.channel().read();
                } else {
                    future.channel().close();
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
        if (outboundChannel != null) {
            closeOnFlush(outboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(context.channel());
    }

    public static void closeOnFlush(Channel channel) {
        if (channel.isActive()) {
            channel.writeAndFlush(Unpooled.EMPTY_BUFFER)
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }
}
