package io.github.thinkframework.server;

import io.github.thinkframework.container.core.StandardContext;

/**
 * 嵌入Spring的Context
 */
public class EmbeddedContext extends StandardContext {
    private JerryStarter jerryStarter;

    public JerryStarter getJerryServletContainerInitializer() {
        return jerryStarter;
    }

    public void setJerryServletContainerInitializer(JerryStarter jerryStarter) {
        this.jerryStarter = jerryStarter;
    }
}
