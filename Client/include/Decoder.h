
#ifndef SPL_CLIENT_DECODER_H
#define SPL_CLIENT_DECODER_H
#include <string>
#include "connectionHandler.h"

using namespace std;

class Decoder{
private:
    ConnectionHandler& connectionHandler;
    bool& work;
    short bytesToshort(char* bytesArr, int pos);

public:
    Decoder(ConnectionHandler &handler, bool& work);
    void run();
};


#endif //SPL_CLIENT_DECODER_H
