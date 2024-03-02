package io.github.thinkframework.net;

import io.github.thinkframework.protocol.SocketEvent;

public abstract class SocketProcessorBase<S> implements Runnable {

    protected SocketWrapperBase<S> socketWrapper;

    protected SocketEvent event;

    public SocketProcessorBase(SocketWrapperBase<S> socketWrapper, SocketEvent event) {
        this.socketWrapper = socketWrapper;
        this.event = event;
    }

    @Override
    public void run() {
        doRun();;
    }

    protected abstract void doRun();
}
