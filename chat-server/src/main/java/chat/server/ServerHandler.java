package chat.server;

import chat.util.DatabaseConnection;
import chat.util.Message;
import chat.util.MsgLogin;
import chat.util.MsgRename;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerHandler extends SimpleChannelInboundHandler {

    private static final Map<String, Channel> mLoggedInUsers = new ConcurrentHashMap<>();
    private String chatNik;
    private int idUser;


    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Клиент коннект - " + ctx.name());
    }

    private boolean authService(MsgLogin msg) {
        try {
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            Statement statement = databaseConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Users where Name=\"" + msg.getName() + "\" and Password=\"" + msg.getPass() + "\"");
            if (resultSet.next()) {
                chatNik = resultSet.getString("NameChat");
                idUser = resultSet.getInt("UserId");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof MsgLogin) {
            if (authService((MsgLogin) msg)) {
                ctx.channel().writeAndFlush(new Message("Server", "true", chatNik));
                for (Map.Entry<String, Channel> pair : mLoggedInUsers.entrySet()) {
                    pair.getValue().writeAndFlush(new Message(chatNik + " entry in chat!"));
                }
                mLoggedInUsers.put(chatNik, ctx.channel());
            } else {
                ctx.channel().writeAndFlush(new Message("Server", "false"));
            }
        }
        if (msg instanceof MsgRename) {
            String oldName = ((MsgRename) msg).getOldName();
            String newName = ((MsgRename) msg).getNewName();
            if (mLoggedInUsers.containsKey(oldName)) {
                mLoggedInUsers.put(newName, mLoggedInUsers.remove(oldName));
                chatNik = newName;
                try {
                    DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
                    Statement statement = databaseConnection.createStatement();
                    int resultUpdate = statement.executeUpdate("UPDATE Users SET  NameChat = \"" + newName + "\" WHERE UserId = " + idUser);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ctx.channel().writeAndFlush(new Message(oldName + " rename to " + newName));
            }
        }
        if (msg instanceof Message) {
            if ("exit".equals(((Message) msg).getMessage().toLowerCase())) {
                mLoggedInUsers.remove(((Message) msg).getName());
                for (Map.Entry<String, Channel> pair : mLoggedInUsers.entrySet()) {
                    pair.getValue().writeAndFlush(new Message(chatNik + " out of chat!"));
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
        if (cause instanceof ReadTimeoutException)
            ctx.writeAndFlush(new Message("Timeout exception!"));
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("Closing connection for client - " + ctx);
    }
}
