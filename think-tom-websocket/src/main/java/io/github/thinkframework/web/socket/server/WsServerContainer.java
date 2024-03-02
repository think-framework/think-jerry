package io.github.thinkframework.web.socket.server;

import io.github.thinkframework.web.socket.client.WsSessionClient;

import javax.websocket.*;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WsServerContainer implements ServerContainer {

    private Map<String,Endpoint> endpoints = new HashMap<>();

    public Endpoint getEndpoint() {
        return endpoints.get(0);
    }

    @Override
    public void addEndpoint(Class<?> endpointClass) throws DeploymentException {
        addEndpoint(ServerEndpointConfig.Builder
                .create(endpointClass,endpointClass.getAnnotation(ServerEndpoint.class).value())
                .build());
    }

    @Override
    public void addEndpoint(ServerEndpointConfig serverConfig) throws DeploymentException {
        try {
            endpoints.put(serverConfig.getPath(), serverConfig.getConfigurator()
                    .getEndpointInstance((Class<? extends Endpoint>)serverConfig.getEndpointClass()));
        } catch (InstantiationException e) {
            throw new DeploymentException("", e);
        }
    }

    @Override
    public long getDefaultAsyncSendTimeout() {
        return 0;
    }

    @Override
    public void setAsyncSendTimeout(long timeoutmillis) {

    }

    @Override
    public Session connectToServer(Object annotatedEndpointInstance, URI path) throws DeploymentException, IOException {
        return new WsSessionClient();
    }

    @Override
    public Session connectToServer(Class<?> annotatedEndpointClass, URI path) throws DeploymentException, IOException {
        return new WsSessionClient();
    }

    @Override
    public Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
        return new WsSessionClient();
    }

    @Override
    public Session connectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
        return new WsSessionClient();
    }

    @Override
    public long getDefaultMaxSessionIdleTimeout() {
        return 0;
    }

    @Override
    public void setDefaultMaxSessionIdleTimeout(long timeout) {

    }

    @Override
    public int getDefaultMaxBinaryMessageBufferSize() {
        return 0;
    }

    @Override
    public void setDefaultMaxBinaryMessageBufferSize(int max) {

    }

    @Override
    public int getDefaultMaxTextMessageBufferSize() {
        return 0;
    }

    @Override
    public void setDefaultMaxTextMessageBufferSize(int max) {

    }

    @Override
    public Set<Extension> getInstalledExtensions() {
        return null;
    }
}
