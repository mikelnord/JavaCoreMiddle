package chat.client;

import java.nio.ByteBuffer;
import java.util.Scanner;


import chat.util.LoggerClient;
import chat.util.Message;
import chat.util.MsgLogin;
import chat.util.MsgRename;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ChatClient {

    static final String HOST = "127.0.0.1";
    static final int PORT = 8189;
    static String clientName;
    static boolean isAuth = false;
    static LoggerClient logger;

    public static void close() {
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast(new ObjectEncoder());
                            channelPipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            channelPipeline.addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            Scanner scanner = new Scanner(System.in);
            Channel channel = channelFuture.sync().channel();
            while (!isAuth) {
                System.out.println("Введите ваше имя и пароль : ");
                if (scanner.hasNext()) {
                    clientName = scanner.nextLine();
                    if (scanner.hasNext()) {
                        String pass = scanner.nextLine();
                        channel.writeAndFlush(new MsgLogin(clientName, pass));
                        channel.flush();
                    }
                }
                Thread.sleep(1000);
            }
            System.out.println("Добро пожаловать в чат: " + clientName);
            logger = new LoggerClient("D:\\Temp\\" + "history_" + clientName + ".txt");
            logger.readFileChannel();
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) {
                    channel.writeAndFlush(new Message(clientName, input));
                    break;
                }
                if (input.startsWith("/")) {
                    if (input.startsWith("/w ")) {
                        String name = input.split("\\s", 3)[1];
                        String message = input.split("\\s", 3)[2];
                        channel.writeAndFlush(new Message(name, message));
                    }
                    if (input.startsWith("/n ")) {
                        String oldName = input.split("\\s", 3)[1];
                        String newName = input.split("\\s", 3)[2];
                        channel.writeAndFlush(new MsgRename(oldName, newName));
                    }
                } else {
                    String mess = clientName + ": " + input;
                    channel.writeAndFlush(new Message(mess));
                    logger.writeFileChannel(ByteBuffer.wrap((mess).getBytes()));
                }
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
