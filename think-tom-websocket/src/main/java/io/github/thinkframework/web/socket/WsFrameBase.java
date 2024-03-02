package io.github.thinkframework.web.socket;

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
public class WsFrameBase {

    protected boolean fin;
    protected int rsv1;
    protected int rsv2;
    protected int rsv3;

    /**
     * 0x0表示附加数据帧
     * 0x1表示文本数据帧
     * 0x2表示二进制数据帧
     * 0x3-7暂时无定义，为以后的非控制帧保留
     * 0x8表示连接关闭
     * 0x9表示ping
     * 0xA表示pong
     * 0xB-F暂时无定义，为以后的控制帧保留
     */
    protected int opcode;

    protected boolean mask;

    protected byte[] maskingKey;

    protected int payLoadLen;
    protected byte[] playLoadData;

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public int getRsv1() {
        return rsv1;
    }

    public void setRsv1(int rsv1) {
        this.rsv1 = rsv1;
    }

    public int getRsv2() {
        return rsv2;
    }

    public void setRsv2(int rsv2) {
        this.rsv2 = rsv2;
    }

    public int getRsv3() {
        return rsv3;
    }

    public void setRsv3(int rsv3) {
        this.rsv3 = rsv3;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    public boolean isMask() {
        return mask;
    }

    public void setMask(boolean mask) {
        this.mask = mask;
    }

    public byte[] getMaskingKey() {
        return maskingKey;
    }

    public void setMaskingKey(byte[] maskingKey) {
        this.maskingKey = maskingKey;
    }

    public int getPayLoadLen() {
        return payLoadLen;
    }

    public void setPayLoadLen(int payLoadLen) {
        this.payLoadLen = payLoadLen;
    }

    public byte[] getPlayLoadData() {
        return playLoadData;
    }

    public void setPlayLoadData(byte[] playLoadData) {
        this.playLoadData = playLoadData;
    }
}
