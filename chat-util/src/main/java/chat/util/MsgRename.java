package chat.util;

import java.io.Serializable;

public class MsgRename implements Serializable {
    private String oldName;
    private String newName;

    public MsgRename(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }
}
