package io.github.thinkframework.container.core;

import io.github.thinkframework.container.Container;
import io.github.thinkframework.container.ContainerBase;
import io.github.thinkframework.container.Wrapper;
import io.github.thinkframework.container.pipeline.standard.StandardPipeline;
import io.github.thinkframework.container.pipeline.standard.StandardWrapperValve;
import io.github.thinkframework.container.pipeline.Pipeline;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

public class StandardWrapper extends ContainerBase implements Wrapper {

    private String name;

    private Servlet servlet;

    public StandardWrapper() {
        pipeline = new StandardPipeline(new StandardWrapperValve(this));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    @Override
    public Pipeline getPipeline() {
        return pipeline;
    }
}
