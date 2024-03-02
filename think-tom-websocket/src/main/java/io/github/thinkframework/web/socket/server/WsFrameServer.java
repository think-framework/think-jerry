package io.github.thinkframework.web.socket.server;

import io.github.thinkframework.connector.nio.NioChannel;
import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.web.socket.WsFrameBase;
import io.github.thinkframework.web.socket.WsFrameClient;
import io.github.thinkframework.web.socket.WsSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

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
public class WsFrameServer extends WsFrameBase {

    private WsSession wsSession;


    private SocketWrapperBase<?> socket;

    private SocketChannel socketChannel;

    public WsFrameServer(WsSession wsSession) {
        this.wsSession = wsSession;
    }

    public void notifyA(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        try {
            int count = socketChannel.read(byteBuffer);
            if(count >0) {
                byteBuffer.flip(); // 从头开始四处理
                byte b = byteBuffer.get();
                fin = (b & 0x80) != 0; // (b & 1000 0000) != 0
                int rsv = (b & 0x70) >>> 4; // (b & 0111 0000) >>> 4
                rsv1 = (rsv >>> 2) & 0x1; // (b >>> 2) & 0000 00001
                rsv2 = (rsv >>> 1) & 0x1; // (b >>> 1) & 0000 00001
                rsv3 = (rsv >>> 0) & 0x1; // (b >>> 0) & 0000 00001
                opcode = b & 0xf; // b & 0000 1111
                b = byteBuffer.get();
                mask = ((b & 0x80) >>> 7) != 0; // ((b & 1000 00000) >>> 7) != 0

                payLoadLen = (b & 0x7f);
                // FIXME 实现这段逻辑
                if(payLoadLen == 126) {

                } else if (payLoadLen == 127) {

                }

                ByteBuffer byteBuffer1 = ByteBuffer.allocate(4);
                if(mask) {
                    maskingKey = new byte[4];
                    byteBuffer.get(maskingKey);
                }

                if (payLoadLen > 0 ) {
                    playLoadData = new byte[payLoadLen];
                    byteBuffer.get(playLoadData);
                }

                byte[] data = new byte[payLoadLen];
                if(mask){
                    for (int i =0 ;i<data.length;i++) {
                        data[i] = (byte) (playLoadData[i] ^ maskingKey[i % 4]);
                    }
                } else {
                    for (int i =0 ;i<data.length;i++) {
                        data[i] = playLoadData[i];
                    }
                }

                System.out.println(new String(data));

                writeA(socketChannel);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeA(SocketChannel socketChannel){
        byte[] bytes = "hello world.".getBytes(StandardCharsets.UTF_8);
        WsFrameClient  wsFrame = new WsFrameClient();
        wsFrame.setFin(true);
        wsFrame.setRsv1(0);
        wsFrame.setRsv2(0);
        wsFrame.setRsv3(0);
        wsFrame.setOpcode(0x1);
        wsFrame.setMask(false);
        wsFrame.setPayLoadLen(bytes.length);
        wsFrame.setPlayLoadData(bytes);
        wsFrame.writeA(socketChannel);

    }

    public SocketWrapperBase<?> getSocket() {
        return socket;
    }

    public void setSocket(SocketWrapperBase<?> socket) {
        this.socket = socket;
        this.socketChannel = ((NioChannel)socket.getSocketChannel()).getSocketChannel();
    }
}
