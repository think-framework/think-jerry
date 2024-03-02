package io.github.thinkframework.net;

import java.nio.channels.AsynchronousSocketChannel;

public class Nio2SocketWrapper extends SocketWrapperBase<AsynchronousSocketChannel>{
    @Override
    public AsynchronousSocketChannel getSocketChannel() {
        return null;
    }
}
