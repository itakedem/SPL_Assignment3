#include "../include/keyBoardParser.h"
#include <string>
#include <boost/lexical_cast.hpp>
#include <iostream>

using namespace std;

void keyBoardParser::init()
{
    mapOpCode["ADMINREG"] = 1;
    mapOpCode["STUDENTREG"] = 2;
    mapOpCode["LOGIN"] = 3;
    mapOpCode["LOGOUT"] = 4;
    mapOpCode["COURSEREG"] = 5;
    mapOpCode["KDAMCHECK"] = 6;
    mapOpCode["COURSESTAT"] = 7;
    mapOpCode["STUDENTSTAT"] = 8;
    mapOpCode["ISREGISTERED"] = 9;
    mapOpCode["UNREGISTER"] = 10;
    mapOpCode["MYCOURSES"] = 11;
}

char*  keyBoardParser::parse(string input)
{
    string opCodeStr = input.substr(0,input.find(' '));
    int pos = input.find(' ');
    string content = "";
    if (pos <= input.size())
        content = input.substr(input.find(' ')+1);
    char* bytes = new char[2+content.size()];
    short opCode = mapOpCode[opCodeStr];
    shortToBytes(opCode,bytes,0);

    if (opCode < 4)
        logRegParser(bytes,content);
    else if (opCode == 8)
        statParser(bytes, content);
    else if (opCode != 4 & opCode != 11)
         FBParser(bytes, content);
    return bytes;
}

void keyBoardParser::logRegParser(char* bytes, string cont)
{
    string name = cont.substr(0,cont.find(' '));
    string pass = cont.substr(cont.find(' ')+1);
    for(int i = 0; i<name.size(); i++)
        bytes[i+2] = name[i];
    int pos = name.size()+2;
    bytes[pos] = '\0';
    pos+=1;
    for(int i = 0; i<pass.size(); i++)
        bytes[i+pos] = pass[i];
}

void keyBoardParser::FBParser(char* bytes, string cont)
{
    short num = strToshort(cont);
    shortToBytes(num,bytes,2);
}

void keyBoardParser::statParser(char* bytes, string cont)
{
    for(int i = 0; i<cont.size(); i++)
        bytes[i+2] = cont[i];
}


void keyBoardParser::shortToBytes(short num, char* bytesArr, int pos)
{
    bytesArr[pos] = ((num >> 8) & 0xFF);
    bytesArr[pos+1] = (num & 0xFF);
}

short keyBoardParser::strToshort(string s)
{
    short output;
    try {output = boost::lexical_cast<short>(s);}
    catch(boost::bad_lexical_cast &){output = 0;}
    return output;
}
