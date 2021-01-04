package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class MyCourseCommand implements MessageCommand
{
    @Override
    public Message execute(Message m, String user_name)
    {
        Database db = Database.getInstance();
        if (user_name == null || db.getUser(user_name).isAdmin())
            return new Message(13, 11, "");
        return new Message(12,11,db.getCourses(user_name));

    }
}
