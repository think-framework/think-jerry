package io.github.thinkframework;

import io.github.thinkframework.adapter.http11.upgrade.Http11UpgradeHandler;
import io.github.thinkframework.web.socket.server.WsServerContainer;
import org.junit.jupiter.api.Test;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

public class WebsocketTest {

    public void serverEndpointConfig(){
//        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(DefaultEndpoint.class,"/ws1")
//                .configurator()
//                .decoders()
//                .encoders()
//                .build();
    }


    public void clientEndpointConfig(){
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create()
//                .configurator()
//                .decoders()
//                .encoders()
                .build();
    }

    public void ddd(){
        Http11UpgradeHandler http11UpgradeHandler = new Http11UpgradeHandler();
//        http11UpgradeHandler.
    }


    @Test
    public void eee() throws DeploymentException {
        WsServerContainer wsServerContainer = new WsServerContainer();
//        wsServerContainer.addEndpoint(DefaultEndpoint.class);
    }
}
