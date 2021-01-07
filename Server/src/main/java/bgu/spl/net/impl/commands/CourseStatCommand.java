package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class CourseStatCommand implements MessageCommand
{


    @Override
    public Message execute(Message m, String user_name)
    {
        Message fail = new Message(13, 7, "");
        if (user_name == null)
            return fail;
        Database db = Database.getInstance();
        String output = db.CourseStat(user_name,m.getCourse_num());
        if (output == null)
            return fail;
        return new Message(12,7,output);
    }
}
