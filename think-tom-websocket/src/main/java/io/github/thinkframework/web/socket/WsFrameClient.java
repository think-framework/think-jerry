package io.github.thinkframework.web.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *       0                   1                   2                   3
 *       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-------+-+-------------+-------------------------------+
 *      |F|R|R|R| opcode|M| Payload len |    Extended payload length    |
 *      |I|S|S|S|  (4)  |A|     (7)     |             (16/64)           |
 *      |N|V|V|V|       |S|             |   (if payload len==126/127)   |
 *      | |1|2|3|       |K|             |                               |
 *      +-+-+-+-+-------+-+-------------+ - - - - - - - - - - - - - - - +
 *      |     Extended payload length continued, if payload len == 127  |
 *      + - - - - - - - - - - - - - - - +-------------------------------+
 *      |                               |Masking-key, if MASK set to 1  |
 *      +-------------------------------+-------------------------------+
 *      | Masking-key (continued)       |          Payload Data         |
 *      +-------------------------------- - - - - - - - - - - - - - - - +
 *      :                     Payload Data continued ...                :
 *      + - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - +
 *      |                     Payload Data continued ...                |
 *      +---------------------------------------------------------------+
 */
public class WsFrameClient extends WsFrameBase{

    public void writeA(SocketChannel socketChannel){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte fin = (byte) ((this.fin ? 0x1 : 0x0) << 7);
        byte rsv  = (byte) (rsv1 << 6 | rsv1 << 5 | rsv1 << 4);
        byte opcode = (byte) this.opcode;

        byte b = (byte) (fin | rsv | opcode);
        System.out.println(Integer.toBinaryString(b));
        byteBuffer.put(b);

        b = (byte) (((this.mask ? 0x1 : 0x0) << 7) | payLoadLen);

        byteBuffer.put(b);

        if (mask) {
            // todo
//            byteBuffer.put();
        }
        byteBuffer.put(playLoadData);
        try {
            socketChannel.write(byteBuffer.flip());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
