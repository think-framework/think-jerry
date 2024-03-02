package io.github.thinkframework.protocol;

/**
 * Java安全套接字扩展（JSSE，Java Secure Socket Extension）
 * 为基于SSL和TLS协议的Java网络应用程序提供了Java API以及参考实现。
 */
public  abstract class AbstractJsseProtocol  extends AbstractProtocol{

    public AbstractJsseProtocol(AbstractEndpoint endpoint) {
        super(endpoint);
    }
}
