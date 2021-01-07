package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class CourseIsRegisterCommand implements MessageCommand
{
    @Override
    public Message execute(Message m, String user_name)
    {
        Message fail = new Message(13, 9, "");
        Database db = Database.getInstance();
        if (user_name == null || db.getCourse(m.getCourse_num()) == null)
            return fail;
        int sign = db.isSignedCourse(user_name, m.getCourse_num());
        if (sign == 2)
            return fail;
        if (sign == 1)
            return new Message(12, 9, "REGISTERED");
        return new Message(12, 9, "NOT REGISTERED");
    }
}
