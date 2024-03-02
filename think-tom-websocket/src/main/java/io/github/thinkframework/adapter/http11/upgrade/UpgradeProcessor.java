package io.github.thinkframework.adapter.http11.upgrade;

import io.github.thinkframework.adapter.AbstractProcessor;
import io.github.thinkframework.adapter.UpgradeToken;
import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractEndpoint;
import io.github.thinkframework.protocol.SocketEvent;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;
import java.io.IOException;

public class UpgradeProcessor extends AbstractProcessor implements WebConnection {


    char s = ':';
    char r = '\r';
    char n = '\n';

    private SocketWrapperBase<?> socket;

    private UpgradeToken upgradeToken;
    private HttpUpgradeHandler httpUpgradeHandler;


    public UpgradeProcessor(SocketWrapperBase<?> socket, UpgradeToken upgradeToken) {
        this.socket = socket;
        this.upgradeToken = upgradeToken;
        this.httpUpgradeHandler = upgradeToken.getHttpUpgradeHandler();
        ((Http11UpgradeHandler) httpUpgradeHandler).setSocket(socket);
    }


    public AbstractEndpoint.Handler.SocketStatus service(SocketWrapperBase<?> socketWrapper) {
        throw new UnsupportedOperationException("websocket.");
    }

    @Override
    public AbstractEndpoint.Handler.SocketStatus dispatch(SocketEvent event) {
        return ((Http11UpgradeHandler)httpUpgradeHandler).dispatch(event);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public UpgradeToken getUpgradeToken() {
        return upgradeToken;
    }

    public SocketWrapperBase<?> getSocket() {
        return socket;
    }

    public void setSocket(SocketWrapperBase<?> socket) {
        this.socket = socket;
    }
}
