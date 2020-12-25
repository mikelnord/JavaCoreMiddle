package chat.server;

import chat.util.Message;
import chat.util.MsgLogin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerHandler extends SimpleChannelInboundHandler {

    private static final Map<String, Channel> mLoggedInUsers = new ConcurrentHashMap<>();
    private static final Map<String, String> users = new ConcurrentHashMap<>();

    static {
        users.put("Player1", "123");
        users.put("Player2", "456");
        users.put("Player3", "123456");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Клиент коннект - " + ctx.name());
    }

    private boolean authService(MsgLogin msg) {
        String pass = users.get(msg.getName());
        if (pass != null) {
            if (pass.equals(msg.getPass())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof MsgLogin) {
            if (authService((MsgLogin) msg)) {
                ctx.channel().writeAndFlush(new Message("Server", "true"));
                for (Map.Entry<String, Channel> pair : mLoggedInUsers.entrySet()) {
                    pair.getValue().writeAndFlush(new Message(((MsgLogin) msg).getName() + " entry in chat!"));
                }
                mLoggedInUsers.put(((MsgLogin) msg).getName(), ctx.channel());
            } else {
                ctx.channel().writeAndFlush(new Message("Server", "false"));
            }
        }
        if (msg instanceof Message) {
            if ("exit".equals(((Message) msg).getMessage().toLowerCase())) {
                mLoggedInUsers.remove(((Message) msg).getName());
                for (Map.Entry<String, Channel> pair : mLoggedInUsers.entrySet()) {
                    pair.getValue().writeAndFlush(new Message(((Message) msg).getName() + " out of chat!"));
                }
                ctx.close();
                return;
            }

            if (((Message) msg).getName().equals("")) {
                for (Map.Entry<String, Channel> pair : mLoggedInUsers.entrySet()) {
                    if (ctx.channel() != pair.getValue())
                        pair.getValue().writeAndFlush(msg);
                }
            } else {
                Channel channel = mLoggedInUsers.get(((Message) msg).getName());
                if (channel != null) {
                    channel.writeAndFlush(msg);
                }
            }
        } else {

        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if(cause instanceof ReadTimeoutException)
        ctx.writeAndFlush(new Message("Timeout exception!"));
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("Closing connection for client - " + ctx);
    }
}
