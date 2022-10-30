package v2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import v2.handler.ClientToServerConnectionHandler;

import java.nio.charset.StandardCharsets;

class ClientToServerConnectionHandlerTest {

    @Test
    public void should_proxy_request_to_outbound_channel () {
        EmbeddedChannel outboundChannel = new EmbeddedChannel();
        EmbeddedChannel inboundChannel = new EmbeddedChannel(new ClientToServerConnectionHandler(channel -> outboundChannel));

        FullHttpRequest inputRequest = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, "/hello");


        ByteBufAllocator byteBufAllocator = inboundChannel.alloc();
        ByteBuf inboundBuffer = byteBufAllocator.buffer(1024);

        inboundBuffer.writeBytes(inputRequest.toString().getBytes(StandardCharsets.UTF_8));
        inboundChannel.writeInbound(inputRequest);

        FullHttpRequest outputRequest = outboundChannel.readOutbound();

        inboundChannel.finish();
        outboundChannel.finish();

        Assertions.assertEquals(inputRequest.uri(), outputRequest.uri());
    }
}