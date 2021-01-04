#include "../include/Decoder.h"
#include "iostream"


bool Decoder::decode(ConnectionHandler *hand)
{
    char bytes[4];
    string cont = "";
    hand->getBytes(bytes, 4);
    originalOP = bytesToshort(bytes,2);
    ansOP = bytesToshort(bytes,0);
    if(ansOP == 13)
        cout << "ERROR " << originalOP << endl;
    else
        {
            if ((originalOP > 5 & originalOP != 10) && !hand->getFrameAscii(cont, '\0'))
                return false;
            cout << "ACK " << originalOP << endl;       //TODO:to move it to the right position
            if (cont.length() != 0)
                cout << cont << endl;
            if (originalOP == 4)
                return false;
            if (originalOP <= 5 | originalOP == 10)
                hand->getBytes(bytes, 1);
        }

    return true;
}

void Decoder::printOptional(char *bytes, int len)
{
    for(int i = 5; i < len; i++)
        cout << bytes[i];
    cout<<""<<endl;
}

short Decoder::getAck(char* bytes)
{
    return bytesToshort(bytes,0);
}

short Decoder::getMes(char* bytes)
{
    return bytesToshort(bytes,2);
}




short Decoder::bytesToshort(char *bytesArr, int pos)
{
    short result = (short)((bytesArr[pos] & 0xff) << 8);
    result += (short)(bytesArr[pos+1] & 0xff);
    return result;
}



//short op_code = bytesToshort(bytes, 0);
//short m_op = bytesToshort(bytes,2);

//return false;
