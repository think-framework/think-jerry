package io.github.thinkframework.example.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "echo", urlPatterns = "/echo")
public class EchoServlet extends GenericServlet {

    private static final Logger logger = LoggerFactory.getLogger(EchoServlet.class);

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        // 请求头
        Enumeration<String> requestHeaderNames = httpServletRequest.getHeaderNames();
        while(requestHeaderNames.hasMoreElements()) {
            String headerName = requestHeaderNames.nextElement();
            logger.debug("Request Header: {}: {}", headerName,httpServletRequest.getHeader(headerName));
        }
        ServletInputStream inputStream = httpServletRequest.getInputStream();
        byte[] bytes = inputStream.readAllBytes();

        logger.debug("Request Body: {}", new String(bytes));

        httpServletResponse.setStatus(HttpStatus.OK.value());

        httpServletResponse.addHeader(HttpHeaders.CONTENT_TYPE,"application/json");
//        httpServletResponse.addHeader(HttpHeaders.CONTENT_LENGTH,String.valueOf(bytes.length));
//        httpServletResponse.addHeader(HttpHeaders.DATE, new Date().toGMTString());
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        servletOutputStream.write(bytes);
        servletOutputStream.flush();
    }
}
