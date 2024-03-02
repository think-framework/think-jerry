package io.github.thinkframework.container.pipeline.standard;

import io.github.thinkframework.container.pipeline.Pipeline;
import io.github.thinkframework.container.pipeline.Valve;

public class StandardPipeline implements Pipeline {

    private StandardContainerValve valve;

    public StandardPipeline(StandardContainerValve valve) {
        this.valve = valve;
    }

    public Valve getFirst() {
        return valve;
    }
}
