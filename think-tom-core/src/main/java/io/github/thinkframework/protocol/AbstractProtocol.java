package io.github.thinkframework.protocol;

import io.github.thinkframework.adapter.Adapter;
import io.github.thinkframework.adapter.Processor;
import io.github.thinkframework.adapter.UpgradeToken;
import io.github.thinkframework.net.SocketWrapperBase;

import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;
import java.util.concurrent.Executor;

public abstract class AbstractProtocol<S> implements ProtocolHandler {

    protected AbstractEndpoint<S,?> endpoint;

    protected AbstractEndpoint.Handler<S> handler;

    protected Adapter adapter;

    protected Executor executor;

    public AbstractProtocol(AbstractEndpoint<S, ?> endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void start() {
        try {
            endpoint.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {

    }

    public abstract Processor creatreProcessor();

    protected abstract Processor createUpgradeProcessor(
            SocketWrapperBase<?> socket,
            UpgradeToken upgradeToken);
    public AbstractEndpoint<S, ?> getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(AbstractEndpoint<S, ?> endpoint) {
        this.endpoint = endpoint;
    }

    public static class ConnectectionHandler<S> implements AbstractEndpoint.Handler<S> {

        private AbstractProtocol<S> protocol;

        public ConnectectionHandler(AbstractProtocol<S> protocol) {
            this.protocol = protocol;
        }


        @Override
        public SocketStatus processor(SocketWrapperBase<S> socketWrapper, SocketEvent event) {
            Processor processor = socketWrapper.getProcessor();
            if(processor == null) { // 创建HTTP处理器
                processor = protocol.creatreProcessor();
            }
            SocketStatus status = SocketStatus.CLOSED;
            do {
                // 处理Websocket握手请求,HTTP协议
                status =  processor.processor(socketWrapper, event);
                if(status == SocketStatus.UPGRADING) { // 协议升级, WebSocket
                    UpgradeToken upgradeToken = processor.getUpgradeToken();
                    //
                    processor = protocol.createUpgradeProcessor(socketWrapper, upgradeToken); // WebSocket处理器

                    // 初始化, 创建Endpoint,调用OnOpen方法
                    HttpUpgradeHandler httpUpgradeHandler = upgradeToken.getHttpUpgradeHandler();
                    httpUpgradeHandler.init((WebConnection) processor);
                    // 绑定Processor
                    socketWrapper.setProcessor(processor);
                }
            } while (status == SocketStatus.UPGRADING);
            return status;
        }
    }

}
