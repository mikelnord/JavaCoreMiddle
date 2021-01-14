package chat.client;


import chat.util.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.ParseException;


public class ClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws IOException, ParseException {
        if (msg instanceof Message) {
            if (((Message) msg).getName().equals("Server")) {
                if (((Message) msg).getMessage().equals("true")) {
                    ChatClient.isAuth = true;
                    ChatClient.clientName = ((Message) msg).getChatName();
                } else
                    System.out.println("Invalid user name or password!");
            } else {
                System.out.println(((Message) msg).getMessage());
                ChatClient.logger.writeFileChannel(ByteBuffer.wrap(((Message) msg).getMessage().getBytes()));
                if (((Message) msg).getMessage().equals("Timeout exception!")) {
                    ChatClient.close();
                }
            }
        }
    }
}

