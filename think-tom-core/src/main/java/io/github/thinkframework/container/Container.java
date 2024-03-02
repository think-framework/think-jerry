package io.github.thinkframework.container;

import io.github.thinkframework.container.pipeline.Pipeline;
import io.github.thinkframework.context.Lifecycle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public interface Container extends Lifecycle {

    Container getParent();


    void setParent(Container parent);

    void addChild(Container child);

    Collection<Container> getChildren();

    void setChildren(Collection<Container> children);

    Pipeline getPipeline();


    void service(HttpServletRequest request, HttpServletResponse response);
}
