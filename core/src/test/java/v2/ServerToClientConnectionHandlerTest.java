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
import v2.handler.ServerToClientConnectionHandler;

import java.nio.charset.StandardCharsets;

class ServerToClientConnectionHandlerTest {

    @Test
    public void should_echo_response_to_outbound_channel() {
        EmbeddedChannel outboundChannel = new EmbeddedChannel();
        outboundChannel.pipeline().addFirst(new ServerToClientConnectionHandler(outboundChannel));

        FullHttpRequest inputRequest = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, "/hello");


        ByteBufAllocator byteBufAllocator = outboundChannel.alloc();
        ByteBuf outputBuffer = byteBufAllocator.buffer(1024);

        outputBuffer.writeBytes(inputRequest.toString().getBytes(StandardCharsets.UTF_8));
        outboundChannel.writeInbound(inputRequest);

        FullHttpRequest outputRequest = outboundChannel.readOutbound();

        outboundChannel.finish();

        Assertions.assertEquals(inputRequest.uri(), outputRequest.uri());
    }
}