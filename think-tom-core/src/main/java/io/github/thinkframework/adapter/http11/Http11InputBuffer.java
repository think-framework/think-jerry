package io.github.thinkframework.adapter.http11;

import io.github.thinkframework.adapter.Request;
import io.github.thinkframework.connector.nio.NioChannel;
import io.github.thinkframework.net.SocketWrapperBase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Http11InputBuffer {
    private final char s = ' ';
    private final char r = '\r';
    private final char n = '\n';

    private Request request;


    private SocketWrapperBase<?> wrapper;

    private ByteBuffer byteBuffer;

    public Http11InputBuffer(Request request) {
        this.request = request;
        request.setInputBuffer(this);
    }

    public void init(SocketWrapperBase<?> wrapper) {
        this.wrapper = wrapper;
    }

    public void parseRequest() {

        NioChannel nioChannel = (NioChannel) wrapper.getSocketChannel();
        SocketChannel socketChannel = nioChannel.getSocketChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // FIXME 不要写死

        try {
            socketChannel.read(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(byteBuffer.hasRemaining()) {
            requestLine(byteBuffer);
        }
        if(byteBuffer.hasRemaining()) {
            rn(byteBuffer);
        }

        if(byteBuffer.hasRemaining()) {
            requestHeader(byteBuffer);
        }

        if(byteBuffer.hasRemaining()) {
            rn(byteBuffer);
        }

        if(byteBuffer.hasRemaining()) {
            requestBody(byteBuffer);
        }
    }

    private void requestLine(ByteBuffer byteBuffer){
        byteBuffer.flip();
        List<String> list = new ArrayList<>();
        list.add(new String());
        String tmp = "";
        for (int i = 0;i < byteBuffer.limit();i++) {
            byte c = byteBuffer.get();
            if(c == s) {
                list.add(new String());
                continue;
            } else if (c == r) {
                c = byteBuffer.get();
                if(c == n) {
                    break;
                }
            }
            list.set(list.size()-1, list.get(list.size()-1).concat(String.valueOf((char) c)));
        }
        list.size();
        request.setMethodValue(list.get(0));
        request.setURI(URI.create(list.get(1)));
    }

    private void requestHeader(ByteBuffer byteBuffer){
//            byteBuffer.flip();
        List<String> list = new ArrayList<>();
        list.add(new String());
        String tmp = "";
        for (int i = 0;i < byteBuffer.limit();i++) {
            byte c = byteBuffer.get();
            if (c == r) {
                c = byteBuffer.get();
                if(c == n) {
                    if(list.get(list.size()-1).length() == 0){
                        break;
                    }
                    list.add(new String());
                    continue;
                }
            }
            list.set(list.size()-1, list.get(list.size()-1).concat(String.valueOf((char) c)));
        }
        list.size();
        for (String header :list) {
            if("".equals(header)){
                continue; // FIXME 黑科技,处理空行,需要移除
            }
            String[] ss = header.split(":");
            request.getHeaders().add(ss[0].trim(),ss[1].trim());
        }
    }


    private void requestBody(ByteBuffer byteBuffer){
        byte[] bytes = new byte[byteBuffer.remaining()];
        ByteBuffer body = byteBuffer.get(bytes);
        request.setBody(new ByteArrayInputStream(bytes));
    }

    private void rn(ByteBuffer byteBuffer){
        byteBuffer.mark();
        byte c = byteBuffer.get();
        if (c == r) {
            c = byteBuffer.get();
            if(c == n) {
                return;
            }
        }
        // 不是换行符就取消
        byteBuffer.reset();
    }
}
