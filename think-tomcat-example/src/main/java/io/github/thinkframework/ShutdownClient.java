package io.github.thinkframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * BIO
 * 基于Socket的客户端
 * 用于关闭服务器
 * @author lixiaobin
 */
public class ShutdownClient {

    protected static final Logger logger = LoggerFactory.getLogger(ShutdownClient.class);

    private static final String shutdown = "SHUTDOWN";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        // 找dns服务器查找域名对应的ip地址
        // 跟服务器上对应端口的程序三次握手，建立链接
        // 构建第一次握手的tcp包发送出去
        // 服务器收到第一次握手的tcp包
        // 回传第二次握手的tcp包
        // 客户端发送第三次握手的tcp包
        // 三次握手成功,建立链接
        socket.connect(new InetSocketAddress("localhost", 38005));

        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()) {

            byte[] request = shutdown.getBytes(StandardCharsets.UTF_8);
            // 请求长度
            outputStream.write(ByteBuffer.allocate(Integer.SIZE/ Byte.SIZE).putInt(request.length).array());
            // 请求体
            outputStream.write(request);
            outputStream.flush();

            byte[] response = new byte[request.length];
            inputStream.read(response);
            logger.info("响应: {}.",new String(response));
        } finally {
            socket.close(); // 四次挥手
        }
    }
}
