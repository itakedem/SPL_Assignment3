#include <stdlib.h>
#include "../include/connectionHandler.h"
#include "../include/keyBoardParser.h"
#include "../include/Decoder.h"
#include <boost/thread.hpp>
#include <iostream>
#include <algorithm>
using namespace std;

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/

bool work = true;

void shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

int bytesLength(string input)
{
    int pos = input.find(' ');
    string content = "";
    if (pos <= input.size())
        content = input.substr(pos+1);
    if(content.size() > 0)
    {
        int amount = count(content.begin(),content.end(), ' ');
        if(amount == 0 & input.substr(0,pos) != "STUDENTSTAT")
            return 4;
        return content.size()+3;
    }
    return 2;
}


void keyBoardInput(ConnectionHandler *connectionHandler) {
    while (work) {
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);
        keyBoardParser keyboard = keyBoardParser();
        keyboard.init();
        int lenbytes = bytesLength(line);
        char *cont = keyboard.parse(line, lenbytes);
        if (!connectionHandler->sendBytes(cont, lenbytes))
        {
            delete cont;
            break;
        }
        delete cont;
    }
    delete connectionHandler;
};


int main (int argc, char *argv[])
{
    if (argc < 3) {
        cerr << "Usage: " << argv[0] << " host port" << std::endl << endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    
    ConnectionHandler *connectionHandler = new ConnectionHandler(host, port);
    if (!connectionHandler->connect()) {
        cerr << "Cannot connect to " << host << ":" << port << endl;
        return 1;
    }
    cout << "Connected!" << endl;

    boost::thread keyBoardInputThread(keyBoardInput, connectionHandler);
    Decoder decoder;

    while (work)
        if (!decoder.decode(connectionHandler))
            work = false;

    keyBoardInputThread.interrupt();
    delete connectionHandler;
    return 0;
}
