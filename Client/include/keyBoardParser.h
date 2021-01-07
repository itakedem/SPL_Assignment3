//
// Created by spl211 on 31/12/2020.
//

#ifndef CLIENT_KEYBOARDPARSER_H
#define CLIENT_KEYBOARDPARSER_H

#include <string>
#include <map>
#include "connectionHandler.h"

using namespace std;

class keyBoardParser{
private:
    map<string, short> mapOpCode;
    ConnectionHandler& connectionHandler;
    bool& work;
    void init();

public:
    keyBoardParser(ConnectionHandler &hand, bool &work);

    void FBParser(char *bytes, string basicString);

    void statParser(char *bytes, string basicString);

    void logRegParser(char *bytes, string basicString);

    void shortToBytes(short num, char *bytesArr, int pos);

    short strToshort(string s);

    void run();

    int bytesLength(string input);

    void parse(string input, char bytes []);
};


#endif //CLIENT_KEYBOARDPARSER_H
