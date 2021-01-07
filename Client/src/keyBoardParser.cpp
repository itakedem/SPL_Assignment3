#include "../include/keyBoardParser.h"
#include <string>
#include <boost/lexical_cast.hpp>
#include <iostream>
#include <boost/thread.hpp>

using namespace std;

keyBoardParser::keyBoardParser(ConnectionHandler &hand, bool &work) : mapOpCode(), connectionHandler(hand), work (work) {init();}

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

void  keyBoardParser::parse(string input, char bytes[])
{
    string opCodeStr = input.substr(0,input.find(' '));
    unsigned int pos = input.find(' ');
    string content = "";
    if (pos <= input.size())
        content = input.substr(input.find(' ')+1);
    short opCode = mapOpCode[opCodeStr];
    shortToBytes(opCode,bytes,0);
    if (opCode < 4)
        logRegParser(bytes,content);
    else if (opCode == 8)
        statParser(bytes, content);
    else if ((opCode > 4) & (opCode < 11) & (opCode!=8))
            FBParser(bytes, content);
}

void keyBoardParser::logRegParser(char bytes[], string cont)
{
    string name = cont.substr(0,cont.find(' '));
    string pass = cont.substr(cont.find(' ')+1);
    for(unsigned int i = 0; i<name.size(); i++)
        bytes[i+2] = name[i];
    unsigned int pos = name.size()+2;
    bytes[pos] = '\0';
    pos+=1;
    for(unsigned int i = 0; i<pass.size(); i++){
        bytes[i+pos] = pass[i];
    }
    bytes[pos+pass.size()]='\0';

}

void keyBoardParser::FBParser(char bytes[], string cont)
{
    short num = strToshort(cont);
    shortToBytes(num,bytes,2);
}

void keyBoardParser::statParser(char bytes[], string cont)
{
    for(unsigned int i = 0; i<cont.size(); i++)
        bytes[i+2] = cont[i];
    bytes[cont.size()+2] = '\0';
}


void keyBoardParser::shortToBytes(short num, char bytesArr[], int pos)
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

int keyBoardParser::bytesLength(string input)
{
    unsigned int pos = input.find(' ');
    string content = "";
    if (pos <= input.size())
        content = input.substr(pos+1);
    if(content.size() > 0)
    {
        int amount = count(content.begin(),content.end(), ' ');
        if(amount == 0 && (input.substr(0,pos) != "STUDENTSTAT"))
            return 4;
        return content.size()+3;
    }
    return 2;
}



void keyBoardParser::run() {
    while (work) {
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);
        int lenbytes = bytesLength(line);
        char cont[lenbytes];
        parse(line, cont);
        if (!connectionHandler.sendBytes(cont, lenbytes))
        {
            cout<<"could not send";
            break;
        }
        if(line == "LOGOUT")
            boost::this_thread::sleep(boost::posix_time::milliseconds(1000));
    }
}



