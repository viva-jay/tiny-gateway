package v2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class GatewayApplication {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(GatewayApplication.class);
    private static final int LOCAL_PORT = Integer.parseInt(System.getProperty("server.v2.port", "80"));
    private static final String CONFIG_FILE = System.getProperty("config.v2.file", "v2/application.yml");

    public static void main(String[] args) throws Exception {
        new GatewayApplication().run();
    }

    void run() {
        int cpu = Runtime.getRuntime().availableProcessors();
        EventLoopGroup bossGroup = new NioEventLoopGroup(cpu);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ProxyServerInitializer(CONFIG_FILE))
                    .bind(LOCAL_PORT).sync()
                    .addListener(future -> logger.info("Gateway initialized with port : {} (http)", LOCAL_PORT))
                    .channel().closeFuture().sync();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
