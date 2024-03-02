package io.github.thinkframework.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Acceptor<U> implements Runnable{

    protected static final Logger logger = LoggerFactory.getLogger(Acceptor.class);
    private AbstractEndpoint<?,U> endpoint;

    public Acceptor(AbstractEndpoint<?, U> endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void run() {
        U socket = null;
        try {
            while(true) { // FIXME 等待链接
                socket = endpoint.accept();
                endpoint.process(socket);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
