package io.github.thinkframework.protocol;

import io.github.thinkframework.adapter.Adapter;

import java.util.concurrent.Executor;

public interface ProtocolHandler {
    Adapter getAdapter();

    void setAdapter(Adapter adapter);

    Executor getExecutor();

    void setExecutor(Executor executor);

    void start();

    void stop();
}
