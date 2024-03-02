package io.github.thinkframework.web.filter;

import io.github.thinkframework.web.socket.UpgradeUtil;
import io.github.thinkframework.web.socket.server.WsServerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * WebSocket Filter
 * 对协议进行升级
 */
@WebFilter(filterName = "webSocket", urlPatterns = "/web-socket")
public class WebSocketFilter extends GenericFilter {
    protected static final Logger logger = LoggerFactory.getLogger(WebSocketFilter.class);
    public static final String UPGRADE = "Upgrade";
    public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
    private WsServerContainer wsServerContainer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!upgrade(httpServletRequest, httpServletResponse, chain)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        Enumeration<String> requestHeaderNames = httpServletRequest.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String headerName = requestHeaderNames.nextElement();
            logger.debug("请求头 {}: {}.", headerName, httpServletRequest.getHeader(headerName));
        }

        logger.debug(UpgradeUtil.getWebSocketAccept(httpServletRequest.getHeader(SEC_WEBSOCKET_KEY)));
        httpServletResponse.setStatus(101); // 协议升级
        UpgradeUtil.doUpgrade(wsServerContainer, httpServletRequest, httpServletResponse, null, null);
    }

    private boolean upgrade(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain) {
        return httpServletRequest.getHeader(HttpHeaders.CONNECTION) != null && UPGRADE.equals(httpServletRequest.getHeader(HttpHeaders.CONNECTION));
    }

}
