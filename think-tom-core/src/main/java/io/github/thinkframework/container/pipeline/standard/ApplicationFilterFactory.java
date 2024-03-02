package io.github.thinkframework.container.pipeline.standard;

import io.github.thinkframework.container.FilterDef;
import io.github.thinkframework.container.Wrapper;
import io.github.thinkframework.container.core.ApplicationFilterChain;
import io.github.thinkframework.container.core.StandardContext;
import io.github.thinkframework.container.core.StandardWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class ApplicationFilterFactory {

    public static ApplicationFilterChain createFilterChain(HttpServletRequest httpServletRequest, Wrapper wrapper, Servlet servlet) {
        ApplicationFilterChain applicationFilterChain = new ApplicationFilterChain();
        StandardContext standardContext = (StandardContext) wrapper.getParent();
        standardContext.getFilters().stream().filter(filterDef ->  "webSocket".equals(filterDef.getName()))
                .map(FilterDef::getFilter)
                .forEach(filter -> applicationFilterChain.addFilter(filter));
        // TODO 修改这个变量名字
        Servlet servlet1 = standardContext.getChildren().stream()
                .map(container -> (StandardWrapper)container)
                .filter(StandardWrapper ->  "echo".equals(StandardWrapper.getName()))
                .map(StandardWrapper::getServlet)
                .findFirst()
                .get();
        applicationFilterChain.setServlet(servlet1);
        return applicationFilterChain;
    }
}
