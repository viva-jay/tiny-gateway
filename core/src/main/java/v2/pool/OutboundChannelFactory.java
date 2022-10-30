package v2.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import v2.ApplicationConfig;
import v2.handler.ServerToClientConnectionHandler;

public class OutboundChannelFactory implements ChannelFactory {
    private final HostNameLoadBalancer hostNameLoadBalancer;
    private final ApplicationConfig applicationConfig;

    public OutboundChannelFactory(ApplicationConfig applicationConfig) {
        this.hostNameLoadBalancer = new HostNameLoadBalancer(applicationConfig.getHostNames());
        this.applicationConfig = applicationConfig;
    }

    public Channel getChannel(Channel inboundChannel) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(inboundChannel.eventLoop())
                .channel(inboundChannel.getClass())
                .handler(new ServerToClientConnectionHandler(inboundChannel))
                .option(ChannelOption.AUTO_READ, false);

        ChannelFuture channelFuture = bootstrap.connect(hostNameLoadBalancer.getNext(), applicationConfig.getPort());
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                inboundChannel.read();
            } else {
                inboundChannel.close();
            }
        });
        return channelFuture.channel();
    }
}
