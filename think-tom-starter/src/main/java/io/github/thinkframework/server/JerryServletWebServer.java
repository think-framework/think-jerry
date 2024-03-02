package io.github.thinkframework.server;


import io.github.thinkframework.Jerry;

import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;

/**
 *
 */
public class JerryServletWebServer implements WebServer {
    private Jerry jerry;

    private boolean autoStart;

    private GracefulShutdown gracefulShutdown;

    private volatile boolean started;

    public JerryServletWebServer(Jerry jerry) {
        this(jerry, true);
    }

    public JerryServletWebServer(Jerry jerry, boolean autoStart) {
        this(jerry, autoStart, Shutdown.IMMEDIATE);
    }


    public JerryServletWebServer(Jerry jerry, boolean autoStart, Shutdown shutdown) {
        this.jerry = jerry;
        this.autoStart = autoStart;

        initialize();
    }

    /**
     * 初始化
     * @throws WebServerException
     */
    private void initialize() throws WebServerException {
        jerry.start();
        startDaemonAwaitThread();
    }

    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread(()-> {
            JerryServletWebServer.this.jerry.getServer().await(); // 等待服务器关闭
            },
                "Jerry-Shutdown-await-Thread");
        awaitThread.setContextClassLoader(getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    /**
     * Starts the web server. Calling this method on an already started server has no
     * effect.
     *
     * @throws WebServerException if the server cannot be started
     */
    @Override
    public void start() throws WebServerException {

    }

    /**
     * Stops the web server. Calling this method on an already stopped server has no
     * effect.
     *
     * @throws WebServerException if the server cannot be stopped
     */
    @Override
    public void stop() throws WebServerException {

    }

    /**
     * Return the port this server is listening on.
     *
     * @return the port (or -1 if none)
     */
    @Override
    public int getPort() {
        return 8888;
    }

}
