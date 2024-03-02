package io.github.thinkframework.connector.nio;

import io.github.thinkframework.adapter.Processor;
import io.github.thinkframework.adapter.UpgradeToken;
import io.github.thinkframework.adapter.http11.Http11Processor;
import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractProtocol;

import java.lang.reflect.InvocationTargetException;

public class Http11NioProtocol extends AbstractProtocol<NioChannel> {
    public Http11NioProtocol() {
        super(new NioEndpoint());
    }

    @Override
    public Processor creatreProcessor() {
        return new Http11Processor(adapter);
    }

    @Override
    protected Processor createUpgradeProcessor(SocketWrapperBase<?> socket, UpgradeToken upgradeToken) {
        Processor processor = null;
        try {
            // TODO 看看是不是要优化,插件机制,动态设置
            Class clazz = this.getClass().getClassLoader()
                    .loadClass("io.github.thinkframework.adapter.http11.upgrade.UpgradeProcessor");

            processor = (Processor) clazz
                    .getConstructor(SocketWrapperBase.class, UpgradeToken.class)
                    .newInstance(socket, upgradeToken);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return processor;
    }
}
