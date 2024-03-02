package io.github.thinkframework.adapter;

import io.github.thinkframework.adapter.http11.Http11InputBuffer;
import io.github.thinkframework.adapter.http11.Http11Processor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpAsyncRequestControl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;

public class Request implements ServerHttpRequest {

    private String methodValue;
    private URI uri;

    private HttpHeaders headers = new HttpHeaders();

    private InputStream body;

    private Http11InputBuffer inputBuffer;

    private Http11Processor processor; // fixme 搞个接口

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public ServerHttpAsyncRequestControl getAsyncRequestControl(ServerHttpResponse serverHttpResponse) {
        return null;
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    public void setBody(InputStream body) {
        this.body = body;
    }

    @Override
    public String getMethodValue() {
        return methodValue;
    }

    public void setMethodValue(String methodValue) {
        this.methodValue = methodValue;
    }


    @Override
    public URI getURI() {
        return uri;
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }


    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    public Http11InputBuffer getInputBuffer() {
        return inputBuffer;
    }

    public void setInputBuffer(Http11InputBuffer inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public Http11Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Http11Processor processor) {
        this.processor = processor;
    }
}
