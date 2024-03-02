package io.github.thinkframework.adapter.http11;

import io.github.thinkframework.adapter.Response;
import io.github.thinkframework.connector.nio.NioChannel;
import io.github.thinkframework.net.SocketWrapperBase;
import org.springframework.http.HttpHeaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Http11OutputBuffer {
    private final char s = ' ';
    private final char r = '\r';
    private final char n = '\n';

    private Response response;
    private SocketWrapperBase<?> wrapper;

    private ByteBuffer body;

    public Http11OutputBuffer(Response response) {
        this.response = response;
        response.setOutputBuffer(this);
    }

    public void init(SocketWrapperBase<?> wrapper) {
        this.wrapper = wrapper;
    }


    /**
     * 请求行
     */
    private ByteBuffer responseLine() {
        ByteBuffer requestLine = ByteBuffer.allocate(1024); // fixme 写死,改掉
        requestLine.put("HTTP/1.1".getBytes(StandardCharsets.UTF_8));
        space(requestLine);
        requestLine.put(String.valueOf(response.getStatusCode().value()).getBytes(StandardCharsets.UTF_8));
        space(requestLine);
        requestLine.put(response.getStatusCode().getReasonPhrase().getBytes(StandardCharsets.UTF_8));
        return requestLine;
    }

    /**
     * 请求头
     */
    private ByteBuffer responseHeader() {
        ByteBuffer requestHeader = ByteBuffer.allocate(1024); // fixme 写死,改掉
        response.getHeaders().forEach((key,values) -> {
            requestHeader.put(key.getBytes(StandardCharsets.UTF_8));
            requestHeader.put(": ".getBytes(StandardCharsets.UTF_8));
            requestHeader.put(values.get(0).getBytes(StandardCharsets.UTF_8)); // fixme 之后修复,现在只能取一个
            rn(requestHeader);
        });
        return requestHeader;
    }

    public void close(){
        try {
            write(((NioChannel) wrapper.getSocketChannel()).getSocketChannel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(SocketChannel socketChannel) throws IOException {
        ByteBuffer responseLine = responseLine();
        ByteBuffer responseHeader = responseHeader();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); //fixme 别写死
        try {
            byteBuffer.put(responseLine.flip());
            byteBuffer.put(rn());
            byteBuffer.put(responseHeader.flip());
//            body.flip();
            if (body.limit() > 0) {
                ByteBuffer contentLength = ByteBuffer.wrap((HttpHeaders.CONTENT_LENGTH + ": " + body.limit()).getBytes(StandardCharsets.UTF_8));
                byteBuffer.put(contentLength);
                byteBuffer.put(rn());
                byteBuffer.put(rn());
                contentLength.clear();
                byteBuffer.put(body);
            }
            byteBuffer.put(rn());
            socketChannel.write(byteBuffer.flip());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            responseLine.clear();
            responseHeader.clear();
            body.clear();
        }
    }

    /**
     * 空格
     * @param byteBuffer
     */
    private ByteBuffer space(ByteBuffer byteBuffer) {
        return byteBuffer.put(" ".getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 换行符
     * @param byteBuffer
     */
    private ByteBuffer rn(ByteBuffer byteBuffer) {
        return byteBuffer.put("\r\n".getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 换行符
     */
    private ByteBuffer rn() {
        return ByteBuffer.wrap("\r\n".getBytes(StandardCharsets.UTF_8));
    }

    public ByteBuffer getBody() {
        return body;
    }

    public void setBody(ByteBuffer body) {
        this.body = body;
    }
}
