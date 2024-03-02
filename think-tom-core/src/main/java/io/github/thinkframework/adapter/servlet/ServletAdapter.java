package io.github.thinkframework.adapter.servlet;

import io.github.thinkframework.adapter.Adapter;
import io.github.thinkframework.adapter.Request;
import io.github.thinkframework.adapter.Response;
import io.github.thinkframework.connector.Connector;

/**
 * Servlet适配器
 */
public class ServletAdapter implements Adapter {

    private Connector connector;

    @Override
    public void service(Request request, Response response) {
        ServletRequestAdapter servletRequest = new ServletRequestAdapter(request);
        ServletResponseAdapter servletResponse = new ServletResponseAdapter(response);

        // TODO 好像没有链接request喝response的方法

        try {
            connector.getService().getContainer()
                    .getPipeline()
                    .getFirst()
                    .service(servletRequest, servletResponse);

            // 把输出写出去
            servletRequest.finishRequest();
            servletResponse.finishResponse();
        } finally {
            // 回收
            servletRequest.recycle();
            servletResponse.recycle();
        }

    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }
}
