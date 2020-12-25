package chat.client;


import chat.util.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Message) {
            if (((Message) msg).getName().equals("Server")) {
                if (((Message) msg).getMessage().equals("true"))
                    ChatClient.isAuth = true;
                else
                    System.out.println("Invalid user name or password!");
            } else {
                System.out.println(((Message) msg).getMessage());
                if(((Message) msg).getMessage().equals("Timeout exception!")){
                    ChatClient.close();
                }
            }
        }
    }
}

