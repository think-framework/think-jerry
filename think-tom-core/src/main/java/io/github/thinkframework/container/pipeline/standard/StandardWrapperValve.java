package io.github.thinkframework.container.pipeline.standard;

import io.github.thinkframework.container.core.StandardWrapper;
import io.github.thinkframework.container.core.ApplicationFilterChain;
import io.github.thinkframework.container.pipeline.Valve;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StandardWrapperValve extends StandardContainerValve implements Valve {

    private ApplicationFilterChain filterChain;

    private Servlet servlet;

    public StandardWrapperValve(StandardWrapper wrapper) {
        super(wrapper);
        servlet = wrapper.getServlet();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            filterChain = ApplicationFilterFactory.createFilterChain(request,(StandardWrapper) container, servlet);
            filterChain.doFilter(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (filterChain != null) {
                filterChain.reset();
            }
        }
    }

    public ApplicationFilterChain getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(ApplicationFilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }
}
