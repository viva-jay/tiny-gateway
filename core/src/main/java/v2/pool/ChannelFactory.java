package v2.pool;

import io.netty.channel.Channel;

public interface ChannelFactory {
    Channel getChannel(Channel channel);
}
