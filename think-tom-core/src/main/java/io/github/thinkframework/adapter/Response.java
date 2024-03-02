package io.github.thinkframework.adapter;

import io.github.thinkframework.adapter.http11.Http11OutputBuffer;
import io.github.thinkframework.adapter.http11.Http11Processor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Response implements ServerHttpResponse {

    private HttpStatus statusCode;

    private HttpHeaders headers = new HttpHeaders();

    private Http11OutputBuffer outputBuffer;

    private ByteArrayOutputStream body = new ByteArrayOutputStream();

    private Http11Processor processor;
    @Override
    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }


    @Override
    public void flush() throws IOException {
        outputBuffer.setBody(ByteBuffer.wrap(body.toByteArray()));
    }

    @Override
    public void close() {
        outputBuffer.close();
    }

    @Override
    public OutputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    public Http11OutputBuffer getOutputBuffer() {
        return outputBuffer;
    }

    public void setOutputBuffer(Http11OutputBuffer outputBuffer) {
        this.outputBuffer = outputBuffer;
    }

    public Http11Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Http11Processor processor) {
        this.processor = processor;
    }
}
