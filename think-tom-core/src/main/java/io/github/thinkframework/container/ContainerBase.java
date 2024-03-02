package io.github.thinkframework.container;

import io.github.thinkframework.container.pipeline.Pipeline;
import io.github.thinkframework.context.Lifecycle;
import io.github.thinkframework.context.LifecycleBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ContainerBase extends LifecycleBase implements Container {

    protected Pipeline pipeline;

    protected Container parent;

    protected Collection<Container> children = new CopyOnWriteArrayList<>();


    @Override
    public Pipeline getPipeline() {
        return pipeline;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Container child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public Collection<Container> getChildren() {
        return children;
    }

    @Override
    public void setChildren(Collection<Container> children) {
        this.children = children;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        pipeline.getFirst().service(request,response);
    }
}
