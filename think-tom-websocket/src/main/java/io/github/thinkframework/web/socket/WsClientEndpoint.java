package io.github.thinkframework.web.socket;

import javax.websocket.*;
import java.lang.annotation.Annotation;

public class WsClientEndpoint implements ClientEndpoint {
    @Override
    public String[] subprotocols() {
        return new String[0];
    }

    @Override
    public Class<? extends Decoder>[] decoders() {
        return new Class[0];
    }

    @Override
    public Class<? extends Encoder>[] encoders() {
        return new Class[0];
    }

    @Override
    public Class<? extends ClientEndpointConfig.Configurator> configurator() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
