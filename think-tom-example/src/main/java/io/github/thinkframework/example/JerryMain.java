package io.github.thinkframework.example;

import io.github.thinkframework.server.JerryServletWebServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 * 会被 spring boot启动类替代
 */
@SpringBootApplication
public class JerryMain {
    protected static final Logger log = LoggerFactory.getLogger(JerryMain.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(JerryMain.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext configurableApplicationContext =  springApplication.run(args);
        JerryServletWebServerFactory jerryServletWebServerFactory = configurableApplicationContext.getBean(JerryServletWebServerFactory.class);
        ServletContextInitializer[] servletContextInitializers = null; //configurableApplicationContext.getBeanFactory().getBeanProvider(ServletContextInitializer.class).stream().toArray();

        WebServer webServer = jerryServletWebServerFactory.getWebServer(servletContextInitializers);
    }
}
