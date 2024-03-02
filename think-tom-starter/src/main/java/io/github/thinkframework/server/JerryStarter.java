package io.github.thinkframework.server;

import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 *
 */
public class JerryStarter implements ServletContainerInitializer {

    private final ServletContextInitializer[] initializers;

    public JerryStarter(ServletContextInitializer[] initializers) {
        this.initializers = initializers;
    }

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        if (initializers != null) {
            for (ServletContextInitializer servletContextInitializer : initializers) {
                try {
                    servletContextInitializer.onStartup(servletContext);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
