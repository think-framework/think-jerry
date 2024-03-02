package io.github.thinkframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 * 启动类
 * 会被 spring boot启动类替代
 */
@EnableWebMvc
@EnableWebSocket
//@EnableWebSocketMessageBroker
@ServletComponentScan
@SpringBootApplication
public class TomcatApplication {
    protected static final Logger log = LoggerFactory.getLogger(TomcatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TomcatApplication.class,args);
    }
}
