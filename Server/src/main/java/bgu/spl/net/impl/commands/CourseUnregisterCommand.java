package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class CourseUnregisterCommand implements MessageCommand
{
    @Override
    public Message execute(Message m, String user_name)
    {
        Message fail = new Message(13, 10, "");
        if (user_name == null)
            return fail;
        Database db = Database.getInstance();
        if (db.unSignCourse(user_name,m.getCourse_num()))
            return new Message(12,10,"");
        return fail;

    }
}
