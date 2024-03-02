package io.github.thinkframework.example.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class DefaultEndpoint extends Endpoint {

    protected final Logger log = LoggerFactory.getLogger(DefaultEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        log.debug("");
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                log.debug("");
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        log.debug("");
    }

    @Override
    public void onError(Session session, Throwable thr) {
        log.debug("");
    }
}
