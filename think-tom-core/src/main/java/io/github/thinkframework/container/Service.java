
package io.github.thinkframework.container;

import io.github.thinkframework.connector.Connector;
import io.github.thinkframework.container.Container;
import io.github.thinkframework.container.Engine;
import io.github.thinkframework.context.Lifecycle;

public interface Service extends Lifecycle {

    Connector[] getConnectors();

    Engine getContainer();


}
