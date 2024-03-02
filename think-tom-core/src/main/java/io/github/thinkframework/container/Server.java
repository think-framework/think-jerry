
package io.github.thinkframework.container;

import io.github.thinkframework.context.Lifecycle;

/**
 * Web服务器
 */
public interface Server extends Lifecycle {

    public void await();
}
