package io.github.thinkframework.adapter.http11;

import io.github.thinkframework.adapter.*;
import io.github.thinkframework.connector.nio.NioChannel;
import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractEndpoint;
import io.github.thinkframework.protocol.SocketEvent;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Http11Processor extends AbstractProcessor {

    char s = ':';
    char r = '\r';
    char n = '\n';

    private UpgradeToken upgradeToken;
    public Http11Processor(Adapter adapter) {
        super(adapter);
    }


    public AbstractEndpoint.Handler.SocketStatus service(SocketWrapperBase<?> socketWrapper) {
        request = new Request();
        response = new Response();

        // TODO 协议升级需要用Processor,写这可能不合适
        request.setProcessor(this);
        request.setProcessor(this);

        Http11InputBuffer http11InputBuffer = new Http11InputBuffer(request);
        Http11OutputBuffer http11OutputBuffer = new Http11OutputBuffer(response);

        http11InputBuffer.init(socketWrapper);
        http11OutputBuffer.init(socketWrapper);

        http11InputBuffer.parseRequest();

        adapter.service(request,response);

        if(upgradeToken != null) {
            return AbstractEndpoint.Handler.SocketStatus.UPGRADING;
        }

        return AbstractEndpoint.Handler.SocketStatus.CLOSED;
    }

    @Override
    public AbstractEndpoint.Handler.SocketStatus dispatch(SocketEvent event) {
        return null;
    }

    public UpgradeToken getUpgradeToken() {
        return upgradeToken;
    }

    public void setUpgradeToken(UpgradeToken upgradeToken) {
        this.upgradeToken = upgradeToken;
    }
}
