package bgu.spl.net.impl.communication;
import bgu.spl.net.api.MessageEncoderDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class EncoderDecoder implements MessageEncoderDecoder<Message>
{
    private byte[] bytes = new byte[1<<10];
    private Message m = null;
    private int len = 0;
    private Short opCode = null;
    private int counter = 0;
    private int locationKeeper;

    @Override
    public Message decodeNextByte(byte nextByte)
    {
        pushByte(nextByte);
        if(len == 2)
            opCode = bytesToShort(bytes,0);
        if((opCode != null) && (len > 2 | opCode == 11 | opCode == 4))
        {
            if(opCode < 4)
                m = lowOpDecode(nextByte);
            else
                m = highOpDecode(nextByte);
        }
        if(m!=null)
            return reset();
        return null;
    }




    private Message reset()
    {
        counter = 0;
        locationKeeper = 0;
        opCode = null;
        len = 0;
        Message out = m;
        m = null;
        return out;
    }

    private Message lowOpDecode(byte nextByte)
    {
        if (nextByte == '\0' & counter == 0)
        {
            counter++;
            locationKeeper = len-1;
        }
        else if (nextByte == '\0' & counter == 1)
        {
            String name = new String(bytes, 2, locationKeeper-2,StandardCharsets.UTF_8);
            String pass = new String(bytes,locationKeeper+1,len - locationKeeper-2,StandardCharsets.UTF_8);
            return new Message(opCode,name,pass);
        }
        return null;
    }

    private Message highOpDecode(byte nextByte)
    {
        if ((opCode == 4 | opCode == 11) & len == 2)            // len maybe 3 or we can delete
            return new Message(opCode);
        if (opCode == 8 & nextByte == '\0')
            return new Message(opCode,new String(bytes,2,len-3,StandardCharsets.UTF_8));
        if (len == 4 & opCode != 8)                 //maybe len = 5
            return new Message(opCode, bytesToShort(bytes, 2));
        return null;
    }

    public short bytesToShort(byte[] byteArr,int i)
    {
        short result = (short)((byteArr[i] & 0xff) << 8);
        result += (short)(byteArr[i+1] & 0xff);
        return result;
    }

    private void pushByte(byte nextByte)
    {
        if (len >= bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);

        bytes[len++] = nextByte;
    }

    @Override
    public byte[] encode(Message message)
    {
        byte[] opcode = shortToBytes((short)message.getOp_code());
        byte[] Mopcode = shortToBytes((short)message.getMessage_op_code());
        bytes = combiner(opcode,Mopcode);
        if (message.getOp_code() == 13)
            return bytes;
        bytes = combiner(bytes,message.getOutput().getBytes(StandardCharsets.UTF_8));
        byte[] b = {'\0'};
        return combiner(bytes,b);

    }

    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }

    private byte[] combiner(byte[] a, byte[] b)
    {
        byte[] output = new byte[a.length+b.length];
        int pos = 0;
        for (byte i : a)
        {
            output[pos] = i;
            pos++;
        }
        for (byte i : b)
        {
            output[pos] = i;
            pos++;
        }
        return output;

    }
}
