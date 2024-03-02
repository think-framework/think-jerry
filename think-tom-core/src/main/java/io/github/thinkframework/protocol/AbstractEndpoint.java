package io.github.thinkframework.protocol;

import io.github.thinkframework.net.SocketProcessorBase;
import io.github.thinkframework.net.SocketWrapperBase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class AbstractEndpoint<S, U> {
    protected static final int count = 10; // Runtime.getRuntime().availableProcessors(); // TODO 修改线程数量

    protected static Executor executor = Executors.newFixedThreadPool(count);

    protected Handler<S> handler;

    protected int port;

    public static interface Handler<S> {
        public enum SocketStatus {
            OPEN, CLOSED, UPGRADING, UPGRADED
        }

        SocketStatus processor(SocketWrapperBase<S> socketWrapper, SocketEvent status);
    }

    public void start() {
        try {
            bind();
            startInternal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void bind() throws Exception;

    public abstract void unbind() throws Exception;

    public abstract void startInternal() throws Exception;

    public abstract void stopInternal() throws Exception;

    public abstract U accept() throws Exception;

    protected abstract SocketProcessorBase<S> createProcessor(SocketWrapperBase<S> socketWrapper, SocketEvent event);

    public abstract boolean process(U u) throws Exception;

    public boolean processSocket(SocketWrapperBase<S> socketWrapper, SocketEvent event) throws Exception {
        SocketProcessorBase<S> socketProcessor = createProcessor(socketWrapper, event);

        executor.execute(socketProcessor);

        return true;
    }

    public Handler<S> getHandler() {
        return handler;
    }

    public void setHandler(Handler<S> handler) {
        this.handler = handler;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
