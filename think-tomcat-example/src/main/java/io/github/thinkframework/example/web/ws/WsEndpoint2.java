package io.github.thinkframework.example.web.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws2")
@Component
public class WsEndpoint2 extends Endpoint implements MessageHandler.Whole<String> {

    protected static final Logger logger = LoggerFactory.getLogger(WsEndpoint2.class);

    private Session session;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        session.addMessageHandler(this);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        // NO-OP by default
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        // NO-OP by default
    }

    @Override
    public void onMessage(String message) {
        logger.debug("{}", message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
