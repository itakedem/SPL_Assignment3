#include <stdlib.h>
#include "../include/connectionHandler.h"
#include "../include/keyBoardParser.h"
#include "../include/Decoder.h"
#include <boost/thread.hpp>
#include <iostream>
#include <algorithm>
using namespace std;

int main (int argc, char *argv[])
{
    if (argc < 3) {
        cerr << "Usage: " << argv[0] << " host port" << std::endl << endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        cerr << "Cannot connect to " << host << ":" << port << endl;
        return 1;
    }
    cout << "Connected!" << endl;
    bool work = true;
    keyBoardParser parser(connectionHandler, work);
    Decoder decoder(connectionHandler, work);

    boost::thread keyBoardInputThread(&keyBoardParser::run, &parser);
    boost::thread decoderThread(&Decoder::run, &decoder);

    keyBoardInputThread.join();
    return 0;
}
