package io.github.thinkframework.example.websocket;


import org.junit.jupiter.api.Test;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpointConfig;

public class WebsocketTest {

    public void serverEndpointConfig(){
        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(DefaultEndpoint.class,"/ws1")
//                .configurator()
//                .decoders()
//                .encoders()
                .build();
    }


    public void clientEndpointConfig(){
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create()
//                .configurator()
//                .decoders()
//                .encoders()
                .build();
    }

    public void ddd(){
//        Http11UpgradeHandler http11UpgradeHandler = new Http11UpgradeHandler();
//        http11UpgradeHandler.
    }


    @Test
    public void eee() throws DeploymentException {
//        WsServerContainer wsServerContainer = new WsServerContainer();
//        wsServerContainer.addEndpoint(DefaultEndpoint.class);
    }
}
