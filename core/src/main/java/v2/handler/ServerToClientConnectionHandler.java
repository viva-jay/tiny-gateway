package v2.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerToClientConnectionHandler extends ChannelInboundHandlerAdapter {
    private final Channel inboundChannel;

    public ServerToClientConnectionHandler(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        context.read();
    }

    @Override
    public void channelRead(final ChannelHandlerContext context, Object messsage) {
        inboundChannel.writeAndFlush(messsage).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                context.channel().read();
            } else {
                future.channel().close();
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
        ClientToServerConnectionHandler.closeOnFlush(inboundChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        ClientToServerConnectionHandler.closeOnFlush(context.channel());
    }
}
