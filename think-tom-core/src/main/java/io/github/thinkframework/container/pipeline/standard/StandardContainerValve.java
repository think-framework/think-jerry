package io.github.thinkframework.container.pipeline.standard;

import io.github.thinkframework.container.Container;
import io.github.thinkframework.container.pipeline.Valve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StandardContainerValve implements Valve {

    protected Container container;
    public StandardContainerValve(Container container) {
        this.container = container;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        container.getChildren().stream().findFirst().get()
                .service(request,response);
    }

}
