package io.github.thinkframework;

import io.github.thinkframework.container.Server;
import io.github.thinkframework.context.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 嵌入式
 */
public class Jerry {

    protected static final Logger logger = LoggerFactory.getLogger(Jerry.class);
    private Server server;

    public void start(){
        if(server instanceof Lifecycle) {
            server.start();
        } else {
            throw new RuntimeException("未实现Lifecycle接口");
        }
    }

    public void stop() {
        if(server instanceof Lifecycle) {
            server.stop();
        } else {
            throw new RuntimeException("未实现Lifecycle接口");
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
