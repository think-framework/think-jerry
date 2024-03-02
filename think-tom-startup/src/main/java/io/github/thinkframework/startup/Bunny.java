package io.github.thinkframework.startup;

import io.github.thinkframework.context.Lifecycle;
import io.github.thinkframework.container.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命令行启动
 * @see io.github.thinkframework.startup.Bootstrap
 */
public class Bunny {

    protected static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    protected boolean await = false;
    protected Server server;

    protected boolean useShutdownHook = true;

    protected Thread shutdownHook = null;

    public void start(){
        if(server instanceof Lifecycle) {
            server.start();
        } else {
            throw new RuntimeException("未实现Lifecycle接口");
        }
        if(useShutdownHook) {
            shutdownHook = new BunnyShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }

        if(await) {
            await();
            stop();
        }
    }

    public void stop() {
        if(server instanceof Lifecycle) {
            server.stop();
        } else {
            throw new RuntimeException("未实现Lifecycle接口");
        }
    }

    public void await() {

        getServer().await();

    }
    protected class BunnyShutdownHook extends Thread {

        @Override
        public void run() {
            if (getServer() != null) {
                Bunny.this.stop();
            }
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
