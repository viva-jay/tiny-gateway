package v2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import v2.handler.ClientToServerConnectionHandler;
import v2.pool.OutboundChannelFactory;

public class ProxyServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ApplicationConfig applicationConfig;
    private final OutboundChannelFactory outboundChannelFactory;

    public ProxyServerInitializer(String configPath) {
        applicationConfig = ApplicationConfig.getApplicationConfig(configPath);
        outboundChannelFactory = new OutboundChannelFactory(applicationConfig);
    }

    @Override
    public void initChannel(SocketChannel channel) {
        channel.config()
                .setAutoRead(false);
        channel.pipeline().addLast(
                new ClientToServerConnectionHandler(outboundChannelFactory));
    }
}
