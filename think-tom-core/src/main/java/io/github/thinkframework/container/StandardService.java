
package io.github.thinkframework.container;

import io.github.thinkframework.connector.Connector;
import io.github.thinkframework.context.Lifecycle;
import io.github.thinkframework.context.LifecycleBase;

public class StandardService extends LifecycleBase implements Service, Lifecycle {

    private Connector[] connectors;

    private Engine container;

    @Override
    public void startInternal() {
        if(container != null && container instanceof Lifecycle) {
            container.start();
        }
        if(connectors != null) { // TODO 空集合处理
            for (Connector connector : connectors) {
                ((Lifecycle) connector).start();
            }
        }
    }

    @Override
    public void stopInternal() {
        if(container != null && container instanceof Lifecycle) {
            container.stop();
        }
        if(connectors != null) { // TODO 空集合处理
            for (Connector connector : connectors) {
                ((Lifecycle) connector).stop();
            }
        }
    }

    @Override
    public Connector[] getConnectors() {
        return connectors;
    }

    public void setConnectors(Connector[] connectors) {
        this.connectors = connectors;
        for (Connector connector : connectors) {
            connector.setService(this);
        }
    }

    @Override
    public Engine getContainer() {
        return container;
    }


    public void setContainer(Engine container) {
        this.container = container;
    }

}
