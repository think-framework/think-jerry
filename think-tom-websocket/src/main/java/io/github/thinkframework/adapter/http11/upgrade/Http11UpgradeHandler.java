package io.github.thinkframework.adapter.http11.upgrade;

import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractEndpoint;
import io.github.thinkframework.protocol.SocketEvent;
import io.github.thinkframework.web.socket.WsEndpointConfig;
import io.github.thinkframework.web.socket.WsSession;
import io.github.thinkframework.web.socket.server.WsFrameServer;
import io.github.thinkframework.web.socket.server.WsServerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;
import javax.websocket.Endpoint;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerContainer;

public class Http11UpgradeHandler implements HttpUpgradeHandler {

    private static final Logger logger = LoggerFactory.getLogger(Http11UpgradeHandler.class);

    private WsServerContainer serverContainer;

    private HandshakeRequest handshakeRequest;


    private SocketWrapperBase<?> socket;

    private WebConnection webConnection;
    /**
     * 数据帧
     */
    private WsFrameServer wsFrameServer;

    private void preInit(ServerContainer serverContainer,
                         HandshakeRequest handshakeRequest){
        this.serverContainer = (WsServerContainer) serverContainer;
        this.handshakeRequest = handshakeRequest;
    }


    @Override
    public void init(WebConnection webConnection) {
        this.webConnection = webConnection;
        UpgradeProcessor upgradeProcessor = (UpgradeProcessor) webConnection;

        WsSession wsSession = new WsSession();
        WsEndpointConfig wsEndpointConfig = new WsEndpointConfig();

        wsFrameServer = new WsFrameServer(wsSession);
        // TODO 放这可能不合适
        wsFrameServer.setSocket(upgradeProcessor.getSocket());

        Endpoint endpoint = serverContainer.getEndpoint();
        endpoint.onOpen(wsSession,wsEndpointConfig);

    }

    @Override
    public void destroy() {

    }

    public AbstractEndpoint.Handler.SocketStatus dispatch(SocketEvent event) {
        wsFrameServer.notifyA();
        return AbstractEndpoint.Handler.SocketStatus.UPGRADED;
    }


    public SocketWrapperBase<?> getSocket() {
        return socket;
    }

    public void setSocket(SocketWrapperBase<?> socket) {
        this.socket = socket;
    }
}
