package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class LogInCommand implements MessageCommand
{
    @Override
    public Message execute(Message m, String user_name)
    {
        if (user_name != null)
            return new Message(13, 3, "");
        Database db = Database.getInstance();
        boolean worked = db.logIn(m.getUser_name(), m.getPassword());

        if (worked)
            return new Message(12, 3, "");
        return new Message(13, 3, "");
    }
}
