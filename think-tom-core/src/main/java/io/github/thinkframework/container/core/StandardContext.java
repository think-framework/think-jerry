package io.github.thinkframework.container.core;

import io.github.thinkframework.adapter.servlet.ApplicationServletContext;
import io.github.thinkframework.container.Container;
import io.github.thinkframework.container.ContainerBase;
import io.github.thinkframework.container.Context;
import io.github.thinkframework.container.FilterDef;
import io.github.thinkframework.container.pipeline.standard.StandardContainerValve;
import io.github.thinkframework.container.pipeline.standard.StandardPipeline;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StandardContext extends ContainerBase implements Context {

    private String path;

    private ServletContext servletContext;

    private Map<ServletContainerInitializer, Set<Class<?>>> servletContainerInitializers = new ConcurrentHashMap<>();

    private Map<String, String> servletMappings = new ConcurrentHashMap<>();


    public StandardContext() {
        pipeline = new StandardPipeline(new StandardContainerValve(this));
    }

    protected Collection<FilterDef> filters = new CopyOnWriteArrayList<>();

    @Override
    protected void startInternal() {
        if(servletContext == null) {
            servletContext = new ApplicationServletContext(this);
        }
        // 启动容器
        for(Container container : children) {
            container.start();
        }

        //
        for(Map.Entry<ServletContainerInitializer, Set<Class<?>>> servletContainerInitializer : servletContainerInitializers.entrySet()) {
            try {
                servletContainerInitializer.getKey()
                        .onStartup(servletContainerInitializer.getValue(), servletContext);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void stopInternal() {
        for(Container container : children) {
            container.stop();
        }
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<ServletContainerInitializer, Set<Class<?>>> getServletContainerInitializers() {
        return servletContainerInitializers;
    }

    @Override
    public void addServletContainerInitializer(
            ServletContainerInitializer servletContainerInitializer, Set<Class<?>> classes) {
        servletContainerInitializers.put(servletContainerInitializer,classes);
    }
    public void setServletContainerInitializers(Map<ServletContainerInitializer, Set<Class<?>>> servletContainerInitializers) {
        this.servletContainerInitializers = servletContainerInitializers;
    }


    public Collection<FilterDef>  getFilters() {
        return filters;
    }

    public void addFilter(FilterDef child) {
        filters.add(child);
    }
    public void setFilters(Collection<FilterDef> filters) {
        this.filters = filters;
    }
}
