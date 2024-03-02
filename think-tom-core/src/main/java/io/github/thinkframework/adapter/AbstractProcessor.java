package io.github.thinkframework.adapter;

import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractEndpoint;
import io.github.thinkframework.protocol.SocketEvent;

public abstract class AbstractProcessor implements Processor{

    protected Adapter adapter;

    protected Request request;

    protected Response response;


    public AbstractProcessor() {
    }

    public AbstractProcessor(Adapter adapter) {
        this(adapter, new Request(), new Response());
    }


    public AbstractProcessor(Adapter adapter, Request request, Response response) {
        this.adapter = adapter;
        this.request = request;
        this.response = response;
    }

    @Override
    public AbstractEndpoint.Handler.SocketStatus processor(SocketWrapperBase<?> socketWrapper, SocketEvent event) {
        AbstractEndpoint.Handler.SocketStatus status = AbstractEndpoint.Handler.SocketStatus.CLOSED;

        if (!isUpgrade()) { // HTTP请求
            status = service(socketWrapper);
        } else { // WebSocket请求
            status = dispatch(event);
        }

        return status;
    }

    @Override
    public boolean isUpgrade() {
        return getUpgradeToken() != null; // token不为空则是WebSocket
    }

    public abstract AbstractEndpoint.Handler.SocketStatus service(SocketWrapperBase<?> socketWrapper);

    public abstract AbstractEndpoint.Handler.SocketStatus dispatch(SocketEvent event);
}
