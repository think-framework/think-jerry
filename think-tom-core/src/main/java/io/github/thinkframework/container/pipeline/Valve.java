package io.github.thinkframework.container.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Valve {

    void service(HttpServletRequest request, HttpServletResponse response);
}
