package bgu.spl.net.impl.communication;
import bgu.spl.net.api.MessageCommand;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.commands.*;


public class Protocol implements MessagingProtocol<Message>
{
    private String user_name = null;
    private boolean shouldTerminate = false;
    private MessageCommand[] commands = commandsInit();

    @Override
    public Message process(Message msg)
    {
        int op_code = msg.getOp_code();
        Message m = commands[op_code].execute(msg,user_name);
        if (op_code == 3 & m.getOp_code() == 12)
            user_name = msg.getUser_name();
        if (op_code == 4 & m.getOp_code() == 12)
        {
            user_name = null;
            shouldTerminate = true;
        }
        return m;
    }

    @Override
    public boolean shouldTerminate() { return shouldTerminate; }

    public MessageCommand[] commandsInit()
    {
        MessageCommand[] commands = new MessageCommand[12];
        commands[1] = new AdminRegisterCommand();
        commands[2] = new UserRegisterCommand();
        commands[3] = new LogInCommand();
        commands[4] = new LogoutCommand();
        commands[5] = new CourseRegisterCommand();
        commands[6] = new CheckKdamCommand();
        commands[7] = new CourseStatCommand();
        commands[8] = new StudentStatCommand();
        commands[9] = new CourseIsRegisterCommand();
        commands[10] = new CourseUnregisterCommand();
        commands[11] = new MyCourseCommand();
        return commands;
    }
}
