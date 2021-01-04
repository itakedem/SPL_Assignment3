#include <stdlib.h>
#include "../include/connectionHandler.h"
#include "../include/keyBoardParser.h"
#include "../include/Decoder.h"
#include <thread>
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
        char *cont = keyboard.parse(line);
        int lenbytes = bytesLength(line);
        if (!connectionHandler->sendBytes(cont, lenbytes))
        {
            cout << "Disconnected. Exiting...\n" << endl;
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

    thread keyBoardInputThread(keyBoardInput, connectionHandler);
    Decoder decoder;

	//From here we will see the rest of the echo client implementation:
    while (work) {
        // We can use one of three options to read data from the server:
        // 1. Read a fixed number of characters
        // 2. Read a line (up to the newline character using the getline() buffered reader
        // 3. Read up to the null character
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end

        if (!decoder.decode(connectionHandler))
        {
            cout << "Disconnected. Exiting...\n" << endl;
            work = false;
        }

		// A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
		// we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
    }
    keyBoardInputThread.detach();
    delete connectionHandler;  // closes the socket connection as well
    cout << "Exiting...\n" << endl;
    return 0;
}

