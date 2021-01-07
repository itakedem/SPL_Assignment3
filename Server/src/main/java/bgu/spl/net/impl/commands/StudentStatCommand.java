package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;

public class StudentStatCommand implements MessageCommand {
    @Override
    public Message execute(Message m, String user_name)
    {
        Message fail = new Message(13,8,"");
        if (user_name == null | m.getStudent_user_name() == null)
            return fail;
        Database db = Database.getInstance();
        String output = db.studentStat(user_name, m.getStudent_user_name());
        if (output == null)
            return fail;
        return new Message(12,8,output);
    }
}
