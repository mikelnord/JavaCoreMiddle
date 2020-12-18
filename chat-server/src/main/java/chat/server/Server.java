package chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;


public final class Server {


    static final int PORT = 8189;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Runnable thread2 = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (ServerHandler.channel != null & scanner.hasNext()) {
                    String input = scanner.nextLine();
                    ServerHandler.channel.writeAndFlush(input);
                }
            }
        };
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
                            channelPipeline.addLast(new StringDecoder());
                            channelPipeline.addLast(new StringEncoder());
                            channelPipeline.addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            System.out.println("Chat Server started. Ready to accept chat clients.");
            thread2.run();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
