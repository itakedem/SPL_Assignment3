package bgu.spl.net.impl.commands;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class CourseRegisterCommand implements MessageCommand
{
    public Message execute(Message m, String user_name)
    {
        Database db = Database.getInstance();
        if (user_name == null || !db.signCourse(m.getCourse_num(), user_name))
            return new Message(13, 5, "");

        return new Message(12, 5, "");
    }
}
