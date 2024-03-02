package io.github.thinkframework.example.web.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws1")
@Component
public class WsEndpoint1 {

    protected static final Logger logger = LoggerFactory.getLogger(WsEndpoint1.class);
    @OnOpen
    public void onOpen(Session session){
        logger.debug("{}", session);
    }


    @OnClose
    public void onClose(Session session){
        logger.debug("{}", session);
    }


    @OnMessage
    public void onMessage(Session session,String message){
        logger.debug("{}", message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @OnError
    public void onError(Session session,Throwable throwable){
        logger.debug("{}", session);
    }
}
