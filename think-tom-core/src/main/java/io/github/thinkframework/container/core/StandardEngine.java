package io.github.thinkframework.container.core;

import io.github.thinkframework.container.Container;
import io.github.thinkframework.container.ContainerBase;
import io.github.thinkframework.container.Engine;
import io.github.thinkframework.container.pipeline.standard.StandardContainerValve;
import io.github.thinkframework.container.pipeline.standard.StandardPipeline;

public class StandardEngine extends ContainerBase implements Engine {

    public StandardEngine() {
        pipeline = new StandardPipeline(new StandardContainerValve(this));
    }

    @Override
    protected void startInternal() {
        for (Container container : children) {
            container.start();
        }
    }

    @Override
    protected void stopInternal() {
        for (Container container : children) {
            container.stop();
        }
    }

    public StandardHost getHost() {
        return (StandardHost) children.stream().findFirst().get();
    }
}
