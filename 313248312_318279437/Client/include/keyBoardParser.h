//
// Created by spl211 on 31/12/2020.
//

#ifndef CLIENT_KEYBOARDPARSER_H
#define CLIENT_KEYBOARDPARSER_H

#include <string>
#include <map>

using namespace std;

static class keyBoardParser{
private:
    map<string, short> mapOpCode;

public:
    void init();
    char * parse(string input);

    void FBParser(char *bytes, string basicString);

    void statParser(char *bytes, string basicString);

    void logRegParser(char *bytes, string basicString);


    void shortToBytes(short num, char *bytesArr, int pos);

    short strToshort(string s);


};


#endif //CLIENT_KEYBOARDPARSER_H
