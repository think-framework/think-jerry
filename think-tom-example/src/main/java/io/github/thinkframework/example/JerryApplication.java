package io.github.thinkframework.example;

import io.github.thinkframework.server.JerryServletWebServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 * 会被 spring boot启动类替代
 */
@ServletComponentScan
@SpringBootApplication
public class JerryApplication {
    protected static final Logger log = LoggerFactory.getLogger(JerryApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(JerryApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);
        springApplication.run(args);
    }
}
