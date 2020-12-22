package chat.util;

import java.io.Serializable;

public class MsgLogin implements Serializable {

    private String name;
    private String pass;

    public MsgLogin(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
