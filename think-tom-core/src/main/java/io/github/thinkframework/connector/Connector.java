package io.github.thinkframework.connector;

import io.github.thinkframework.adapter.Adapter;
import io.github.thinkframework.context.LifecycleBase;
import io.github.thinkframework.protocol.ProtocolHandler;
import io.github.thinkframework.container.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connector extends LifecycleBase {

    protected static final Logger logger = LoggerFactory.getLogger(Connector.class);

    private Service service;

    private ProtocolHandler protocolHandler;

    private Adapter adapter;

    private int port;

    @Override
    protected void startInternal() {
        logger.debug("连接器启动.");
        if(protocolHandler != null) {
            protocolHandler.start();
        }
    }

    @Override
    protected void stopInternal() {
        logger.debug("连接器关闭.");
        if(protocolHandler != null) {
            protocolHandler.stop();
        }
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ProtocolHandler getProtocolHandler() {
        return protocolHandler;
    }

    public void setProtocolHandler(ProtocolHandler protocolHandler) {
        this.protocolHandler = protocolHandler;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
