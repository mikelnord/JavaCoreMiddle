package chat.util;

import java.io.Serializable;

public class Message implements Serializable {
    private final String name;
    private final String message;
    private final String chatName;

    public Message(String name, String message, String chatName) {
        this.name = name;
        this.message = message;
        this.chatName = chatName;
    }

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
        chatName = "";
    }

    public Message(String message) {
        this.message = message;
        name = "";
        chatName = "";
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getChatName() {
        return chatName;
    }
}
