package io.github.thinkframework.container.core;


import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ApplicationFilterChain implements FilterChain {

    private List<Filter> filters = new CopyOnWriteArrayList<>();

    public volatile int pos = 0;
    private volatile int n = 0;

    private Servlet servlet;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if(pos < n) {
            filters.get(pos++).doFilter(request,response,this);
        }else {
            servlet.service(request, response);
        }
    }

    public Servlet getServlet() {
        return servlet;
    }

    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    public void addFilter(Filter filter){
        filters.add(filter);
        n++; // filter数量
    }

    public void reset(){
        filters.clear();
        servlet = null;
        n = 0;
    }
}
