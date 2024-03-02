package io.github.thinkframework.example.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws")
@Component
public class DefaultEndpoint extends Endpoint {

    protected final Logger log = LoggerFactory.getLogger(DefaultEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        log.debug("");
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                log.debug(message);
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
