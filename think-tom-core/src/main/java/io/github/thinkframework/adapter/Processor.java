package io.github.thinkframework.adapter;

import io.github.thinkframework.net.SocketProcessorBase;
import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractEndpoint;
import io.github.thinkframework.protocol.SocketEvent;

public interface Processor {

    AbstractEndpoint.Handler.SocketStatus processor(SocketWrapperBase<?> socketWrapper, SocketEvent socketEvent);

    UpgradeToken getUpgradeToken();

    boolean isUpgrade();
}
