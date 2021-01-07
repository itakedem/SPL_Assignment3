package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class AdminRegisterCommand implements MessageCommand
{

    @Override
    public Message execute(Message m, String user_name)
    {
        if (user_name != null)      //already logged in
            return new Message(13, 1, "");
        Database db = Database.getInstance();
        boolean worked = db.register(m.getUser_name(), m.getPassword(), true);

        if (worked)
            return new Message(12, 1, "");
        return new Message(13, 1, "");
    }
}
