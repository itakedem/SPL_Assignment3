package bgu.spl.net.impl.commands;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.srv.Database;

public class UserRegisterCommand implements MessageCommand {

    @Override
    public Message execute(Message m, String user_name)
    {
        if (user_name != null)
            return new Message(13, 2, "");
        Database db = Database.getInstance();
        boolean worked = db.register(m.getUser_name(), m.getPassword(), false);
        if (worked)
            return new Message(12, 2, "");
        return new Message(13, 2, "");
    }
}
