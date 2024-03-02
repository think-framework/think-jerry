package io.github.thinkframework.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("think.web")
public class JerryWebServerProperteis {

    private Server server;
    private Endpoint endpoint;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public class Server{
        private int port;

        public Server() {

        }

        public Server(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }


    public class Endpoint{
        private int port;

        public Endpoint() {

        }
        public Endpoint(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

}
