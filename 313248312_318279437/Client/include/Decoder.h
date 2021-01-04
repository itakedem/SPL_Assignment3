
#ifndef SPL_CLIENT_DECODER_H
#define SPL_CLIENT_DECODER_H
#include <string>
#include "connectionHandler.h"

using namespace std;

class Decoder{
public:
    bool decode(ConnectionHandler *handler);
    short getAck(char *bytes);

    short getMes(char *bytes);


private:
    short originalOP;
    short ansOP;
    short bytesToshort(char* bytesArr, int pos);
    void printOptional(char *bytes, int len);

};


#endif //SPL_CLIENT_DECODER_H
