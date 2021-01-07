package bgu.spl.net.api;

import bgu.spl.net.impl.communication.Message;

import java.io.Serializable;

public interface MessageCommand extends Serializable
{
    Message execute(Message m, String user_name);
}
