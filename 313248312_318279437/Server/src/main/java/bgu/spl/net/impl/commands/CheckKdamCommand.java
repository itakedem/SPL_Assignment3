package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

import java.io.Serializable;
import java.util.LinkedList;

public class CheckKdamCommand implements MessageCommand
{

    public Message execute(Message m, String user_name)
    {
        Database db = Database.getInstance();
        if (user_name == null || db.getCourse(m.getCourse_num()) == null || db.getUser(user_name).isAdmin())
            return new Message(13, 6, "");
        LinkedList<Integer> kdam = db.kdamCheck(m.getCourse_num());
        return new Message(12, 6, kdam.toString());
    }
}
