package io.github.thinkframework.web.socket.server;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WsHandshakeRequest implements HandshakeRequest {

    private Map<String, List<String>> headers = new ConcurrentHashMap<>();
    public WsHandshakeRequest(HttpServletRequest httpServletRequest) {
        httpServletRequest.getUserPrincipal();
        httpServletRequest.getRequestURI();
//        httpServletRequest.isUserInRole();

    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public URI getRequestURI() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Object getHttpSession() {
        return null;
    }

    @Override
    public Map<String, List<String>> getParameterMap() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
