package io.github.thinkframework.web.socket;

import io.github.thinkframework.web.socket.server.WsServerContainer;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

public class WsContainerProvider extends ContainerProvider {
    @Override
    protected WebSocketContainer getContainer() {
        return new WsServerContainer();
    }
}
