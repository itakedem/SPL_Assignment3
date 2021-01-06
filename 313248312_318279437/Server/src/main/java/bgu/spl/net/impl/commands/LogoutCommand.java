package bgu.spl.net.impl.commands;

import bgu.spl.net.api.Command;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;


public class LogoutCommand implements MessageCommand
{

    public Message execute(Message m, String user_name)
    {

        if (user_name != null)
        {
            Database db = Database.getInstance();
            db.getUser(user_name).logOut();
            return new Message(12, 4, "");
        }
        return new Message(13, 4, "");

    }
}
