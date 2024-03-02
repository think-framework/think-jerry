package io.github.thinkframework.web.socket;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WsHandshakeResponse implements HandshakeResponse {
    private Map<String, List<String>> headers = new ConcurrentHashMap<>();

    public WsHandshakeResponse(HttpServletResponse httpServletResponse) {

    }
    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
