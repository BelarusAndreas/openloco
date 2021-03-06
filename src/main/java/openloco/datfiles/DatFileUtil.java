package openloco.datfiles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DatFileUtil {

    public static long readUint32LE(byte[] bytes, int pointer) {
        return readUintLE(bytes, pointer, 4);
    }

    public static long readUintLE(byte[] bytes, int pointer, int size) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, pointer, size);
        buffer.put(new byte[8-size]);
        buffer.flip();
        return buffer.order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    public static byte[] rleDecode(byte[] input, int offset, long length) {
        int decodedLength = calculateRleLength(input, offset, length);

        int decodedOffset = 0;
        byte[] decoded = new byte[decodedLength];
        while (length > 0) {
            byte rle = input[offset++];
            length--;

            if (rle < 0) {
                //run is 1-rle as rle is < 0
                int run = 1-rle;
                //set this run to a particular value
                byte value = input[offset++];
                Arrays.fill(decoded, decodedOffset, decodedOffset+run, value);
                length--;
                decodedOffset += run;
            }
            else {
                int run = 1+rle;
                //copy this run from the input
                System.arraycopy(input, offset, decoded, decodedOffset, run);
                length-=run;
                offset+=run;
                decodedOffset += run;
            }
        }
        return decoded;
    }

    private static int calculateRleLength(byte[] input, int offset, long length) {
        int decodedLength = 0;
        while (length > 0) {
            byte rle = input[offset++];
            length--;

            if (rle < 0) {
                //set this run to a particular value
                int run = 1-rle;
                offset++;
                length--;
                decodedLength += run;
            }
            else {
                //copy this run from the input
                int run = rle + 1;
                decodedLength+= run;
                length-= run;
                offset+= run;
            }
        }
        return decodedLength;
    }

    public static byte[] descramble(byte[] input, int pointer, long length) {
        byte[] result = new byte[(int)length];
        byte bits = 1;
        for (int ofs=pointer; ofs<pointer+length; ofs++) {
            result[ofs-pointer] = rotr8(input[ofs], bits);
            bits = (byte)((bits+2)&7);
        }
        return result;
    }

    public static byte[] decompress(byte[] chunk) {
        List<Byte> buffer = new ArrayList<>();
        int i=0;
        while (i < chunk.length) {
            byte code = chunk[i++];
            if (code == (byte)0xFF) {
                buffer.add(chunk[i++]);
            }
            else {
                int len = (code & 7)+1;
                int ofs = 32 - (code >> 3);

                int start = buffer.size()-ofs;
                int end = start+len;

                for (int j=start; j<end; j++) {
                    buffer.add(buffer.get(start));
                }
            }
        }
        return toArray(buffer);
    }

    private static byte rotr8(byte a, byte n) {
        return (byte)(0xFF & (((a)>>(n))|((a)<<(8-n))));
    }

    private static byte[] toArray(List<Byte> buffer) {
        byte[] result = new byte[buffer.size()];
        for (int i=0; i<buffer.size(); i++) {
            result[i] = buffer.get(i);
        }
        return result;
    }

}
