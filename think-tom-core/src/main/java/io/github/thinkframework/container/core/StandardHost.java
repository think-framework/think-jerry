package io.github.thinkframework.container.core;

import io.github.thinkframework.container.Container;
import io.github.thinkframework.container.ContainerBase;
import io.github.thinkframework.container.Host;
import io.github.thinkframework.container.pipeline.standard.StandardContainerValve;
import io.github.thinkframework.container.pipeline.standard.StandardPipeline;

public class StandardHost extends ContainerBase implements Host {

    public StandardHost() {
        pipeline = new StandardPipeline(new StandardContainerValve(this));
    }

    @Override
    protected void startInternal() {
        for(Container container : children) {
            container.start();
        }
    }

    @Override
    protected void stopInternal() {
        for(Container container : children) {
            container.stop();
        }
    }
}
