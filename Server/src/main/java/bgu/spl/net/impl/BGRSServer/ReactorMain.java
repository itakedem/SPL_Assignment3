package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.communication.EncoderDecoder;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.impl.communication.Protocol;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.Reactor;

public class ReactorMain
{
    public static void main(String[] args)
    {
        int port = Integer.parseInt(args[0]);
        int threads = Integer.parseInt(args[1]);
        Reactor<Message> reactor = new Reactor<Message>(threads, port, ()->new Protocol(),()->new EncoderDecoder());
        if (!Database.getInstance().initialize("./Courses.txt"))
        {
            System.out.println("Unable to read data from file");
            return;
        }

        reactor.serve();
    }

}
