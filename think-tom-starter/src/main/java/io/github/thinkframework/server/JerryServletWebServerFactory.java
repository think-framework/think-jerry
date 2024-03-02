package io.github.thinkframework.server;

import io.github.thinkframework.Jerry;
import io.github.thinkframework.adapter.servlet.ApplicationServletContext;
import io.github.thinkframework.adapter.servlet.ServletAdapter;
import io.github.thinkframework.connector.Connector;
import io.github.thinkframework.connector.nio.Http11NioProtocol;
import io.github.thinkframework.connector.nio.NioEndpoint;
import io.github.thinkframework.container.core.StandardEngine;
import io.github.thinkframework.container.core.StandardHost;
import io.github.thinkframework.protocol.AbstractProtocol;
import io.github.thinkframework.server.config.JerryWebServerProperteis;
import io.github.thinkframework.container.StandardServer;
import io.github.thinkframework.container.StandardService;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ServletWeb服务器工厂
 * 实现此接口可以防止Tomcat的自动装备
 * @see org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration
 *
 */
public class JerryServletWebServerFactory extends AbstractServletWebServerFactory implements ConfigurableJerryWebServerFactory {

    private JerryWebServerProperteis jerryWebServerProperteis;

    public JerryServletWebServerFactory() {
    }

    public JerryServletWebServerFactory(int port) {
        super(port);
    }

    public JerryServletWebServerFactory(String contextPath, int port) {
        super(contextPath, port);
    }

    public JerryServletWebServerFactory(JerryWebServerProperteis jerryWebServerProperteis) {
        this.jerryWebServerProperteis = jerryWebServerProperteis;
    }

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        Jerry jerry = new Jerry();
        // 配置链接器
        Connector connector = configConnector();
        // 配置容器
        StandardEngine engine = configEngine();

        /// 配置服务
        StandardService service = configService();
        service.setConnectors(new Connector[]{connector}); //设置连接器
        service.setContainer(engine); // 设置容器

        // 配置服务器
        StandardServer server = configServer();
        server.setServices(Stream.of(service)
                .collect(Collectors.toList())); // 设置服务

        prepareContext(engine.getHost(),initializers);

        jerry.setServer(server); // 设置服务器
        return new JerryServletWebServer(jerry, getPort() >= 0, getShutdown());
    }


    private Connector configConnector() {
        // servlet适配器
        ServletAdapter adapter = new ServletAdapter();

        NioEndpoint endpoint = new NioEndpoint();
        endpoint.setPort(getPort());
        Http11NioProtocol protocol = new Http11NioProtocol();
        protocol.setEndpoint(endpoint);

        endpoint.setHandler(new AbstractProtocol.ConnectectionHandler(protocol));

        protocol.setAdapter(adapter);
        // 连接器
        Connector connector = new Connector();

        connector.setProtocolHandler(protocol);
        connector.setAdapter(adapter);
        connector.setPort(38080);

        adapter.setConnector(connector);

        return connector;
    }

    private StandardEngine configEngine() {
        StandardHost host = new StandardHost();

        StandardEngine engine = new StandardEngine();
        engine.addChild(host);
        return engine;
    }

    private StandardService configService() {
        StandardService service = new StandardService();
        return service;
    }
    private StandardServer configServer() {
        // 服务器
        StandardServer server = new StandardServer();

        server.setPort(38005);
        server.setShutdown("SHUTDOWN");
        return server;
    }


    protected void prepareContext(StandardHost host, ServletContextInitializer... initializers) {
        EmbeddedContext context = new EmbeddedContext();
        context.setPath(""); // 先这么滴
        ApplicationServletContext servletContext = new ApplicationServletContext(context);
        host.addChild(context);

        configureContext(context,initializers);
        postProcessContext(context);
    }

    protected void configureContext(EmbeddedContext context, ServletContextInitializer... initializers) {
        JerryStarter jerryStarter = new JerryStarter(initializers);
        context.setJerryServletContainerInitializer(jerryStarter);

        context.addServletContainerInitializer(jerryStarter, Collections.emptySet());
    }

    protected void postProcessContext(EmbeddedContext context) {
    }
}
