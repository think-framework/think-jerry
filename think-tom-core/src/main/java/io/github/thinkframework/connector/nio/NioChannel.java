package io.github.thinkframework.connector.nio;

import java.nio.channels.SocketChannel;

public class NioChannel {
    private SocketChannel socketChannel;

    public NioChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
