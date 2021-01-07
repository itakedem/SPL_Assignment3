#include "../include/Decoder.h"
#include "iostream"



Decoder::Decoder(ConnectionHandler &handler, bool &work): connectionHandler(handler), work(work) {}


void Decoder::run() {
    while (work) {
        char bytes[4];
        string cont = "";
        connectionHandler.getBytes(bytes, 4);
        short originalOP = bytesToshort(bytes, 2);
        short ansOP = bytesToshort(bytes, 0);
        if (ansOP == 13)
            cout << "ERROR " << originalOP << endl;
        else {
            if (((originalOP > 5) & (originalOP != 10)) && !connectionHandler.getFrameAscii(cont, '\0'))
                work = false;
            cout << "ACK " << originalOP << endl;
            if (cont.length() != 0)
                cout << cont << endl;
            if (originalOP == 4)
                work = false;
            if ((originalOP <= 5) | (originalOP == 10))
                connectionHandler.getBytes(bytes, 1);
        }
    }
}



short Decoder::bytesToshort(char *bytesArr, int pos)
{
    short result = (short)((bytesArr[pos] & 0xff) << 8);
    result += (short)(bytesArr[pos+1] & 0xff);
    return result;
}



