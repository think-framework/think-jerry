package io.github.thinkframework.net;

import io.github.thinkframework.adapter.Processor;

import java.util.concurrent.atomic.AtomicReference;

public abstract class SocketWrapperBase <S>{

    private AtomicReference<Processor> processor = new AtomicReference<>();

    public abstract S getSocketChannel();


    public Processor getProcessor() {
        return processor.get();
    }

    public void setProcessor(Processor processor) {
        this.processor.set(processor);
    }
}
