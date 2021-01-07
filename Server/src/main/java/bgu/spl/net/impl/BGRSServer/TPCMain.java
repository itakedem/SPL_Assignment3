package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.communication.EncoderDecoder;
import bgu.spl.net.impl.communication.Message;
import bgu.spl.net.impl.communication.Protocol;
import bgu.spl.net.srv.BaseServer;
import bgu.spl.net.srv.Database;

public class TPCMain
{
    public static void main(String[] args)
    {
        int port = Integer.parseInt(args[0]);
        BaseServer<Message> tpc = BaseServer.TPC(port,()->new Protocol(),()->new EncoderDecoder());
        if (!Database.getInstance().initialize("./Courses.txt"))
        {
            System.out.println("Unable to read data from file");
            return;
        }

        tpc.serve();
    }
}
