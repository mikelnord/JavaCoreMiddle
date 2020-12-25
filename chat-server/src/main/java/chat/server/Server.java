package chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;


public final class Server {

    static final int PORT = 8189;

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(120));
                            channelPipeline.addLast(new ObjectEncoder());
                            channelPipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            channelPipeline.addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            System.out.println("Chat Server started. Ready to accept chat clients.");
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }
}
