package chat.util;

import java.io.Serializable;

public class Message implements Serializable {
    private final String name;
    private final String message;


    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public Message(String message) {
        this.message = message;
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
