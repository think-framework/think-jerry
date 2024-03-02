package io.github.thinkframework.connector.nio;

import io.github.thinkframework.net.SocketProcessorBase;
import io.github.thinkframework.net.SocketWrapperBase;
import io.github.thinkframework.protocol.AbstractEndpoint;
import io.github.thinkframework.protocol.Acceptor;
import io.github.thinkframework.protocol.SocketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * NIO
 * ServerSocketChannel
 */
public class NioEndpoint extends AbstractEndpoint<NioChannel,SocketChannel> {


    private static final Logger logger = LoggerFactory.getLogger(NioEndpoint.class);

    private BlockingQueue<SelectionKey> blockingQueue = new LinkedBlockingQueue();

    private ServerSocketChannel serverSocketChannel;

    private Poller poller;


    //    public void start() {
//        try {
//            running = true;
//
//            // 监听3次握手
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//            ServerSocket serverSocket = serverSocketChannel.socket();
//
////        for (int i=0;i<count;i++) {
////            executorService.submit(new Worker());
////        }
//

//        } catch (ClosedChannelException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void bind() throws Exception {

        serverSocketChannel = ServerSocketChannel.open();
        // TODO 监听端口
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        logger.debug("监听地址: {}", new InetSocketAddress("localhost", port));
        serverSocketChannel.configureBlocking(true);
    }

    @Override
    public void unbind() throws Exception {

    }

    @Override
    public void startInternal() throws Exception {
        logger.debug("NIO断点启动.");
        poller = new Poller();
        Thread pollerThread = new Thread(poller,"Think-Jerry-Server Poller Thread");
        pollerThread.setDaemon(true); // todo 统一设置
        pollerThread.start();

        Acceptor acceptor = new Acceptor(this);
        Thread acceptorThread = new Thread(acceptor,"Think-Jerry-Server Acceptor Thread");
        acceptorThread.setDaemon(true); // todo 统一设置
        acceptorThread.start();
    }

    @Override
    public void stopInternal() throws Exception {
        logger.debug("NIO断点关闭.");
    }

    @Override
    public SocketChannel accept() throws Exception {
        return serverSocketChannel.accept();
    }

    public boolean process(SocketChannel socketChannel) throws Exception {
        socketChannel.configureBlocking(false);
        NioSocketWrapper socketWrapper = new NioSocketWrapper(new NioChannel(socketChannel));
        poller.register(socketWrapper);
        return false;
    }

    @Override
    protected SocketProcessorBase<NioChannel> createProcessor(SocketWrapperBase<NioChannel> socketWrapper, SocketEvent event) {
        return new NioSocketProcessor(socketWrapper, event);
    }

    private void initSocket(){
        
    }

    public Poller getPoller() {
        return poller;
    }

    public void setPoller(Poller poller) {
        this.poller = poller;
    }

    public class PollEvent {

        private NioSocketWrapper socketWrapper;

        private int interestOps;

        public PollEvent(NioSocketWrapper socketWrapper, int interestOps) {
            this.socketWrapper = socketWrapper;
            this.interestOps = interestOps;
        }

        public NioSocketWrapper getSocketWrapper() {
            return socketWrapper;
        }

        public void setSocketWrapper(NioSocketWrapper socketWrapper) {
            this.socketWrapper = socketWrapper;
        }

        public int getInterestOps() {
            return interestOps;
        }

        public void setInterestOps(int interestOps) {
            this.interestOps = interestOps;
        }
    }
    public class Poller implements Runnable {
        private Selector selector;

        private LinkedBlockingQueue<PollEvent> events = new LinkedBlockingQueue<>();
        public Poller() throws IOException {
            selector = Selector.open();
        }

        public void register(NioSocketWrapper socketWrapper) {
            events.offer(new PollEvent(socketWrapper,SelectionKey.OP_ACCEPT));
        }

        @Override
        public void run() {
            while (true) {
                PollEvent event = events.poll();
                if (event != null) {
                    NioSocketWrapper socketWrapper = event.getSocketWrapper();
                    NioChannel channel = socketWrapper.getSocketChannel();
                    SocketChannel socketChannel = channel.getSocketChannel();
                    try {
                        socketChannel.register(selector, SelectionKey.OP_READ, socketWrapper);
                    } catch (ClosedChannelException e) {
                        throw new RuntimeException(e);
                    }

                }

                // ☆重要, 这样才能监听后续请求
                int count = 0;
                try {
                    // ☆重要
                    count = selector.selectNow();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    if (selectionKeys != null) {
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        if (iterator.hasNext()) {
                            do {
                                SelectionKey selectionKey = iterator.next();
                                iterator.remove();
                                processKey(selectionKey, (NioSocketWrapper) selectionKey.attachment());
                            } while (iterator.hasNext());
                        }
                    }
                }
            }
        }

        public void processKey(SelectionKey selectionKey,NioSocketWrapper socketWrapper){
            if(selectionKey.isValid()){
                if(selectionKey.isReadable() || selectionKey.isWritable()) {
                    // FIXME 长链接不需要这样的逻辑可能
                    unReg(selectionKey,socketWrapper,selectionKey.readyOps()); // 取消注册
                    try {
                        if (selectionKey.isReadable()) {
                            processSocket(socketWrapper,SocketEvent.OPEN_READ);
                        } else if (selectionKey.isWritable()) {
                            processSocket(socketWrapper, SocketEvent.OPEN_WRITE);
                        }
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 取消注册
         * 取反
         * @param selectionKey
         * @param socketWrapper
         */
        public void unReg(SelectionKey selectionKey, NioSocketWrapper socketWrapper, int readOps) {
            reg(selectionKey, socketWrapper, selectionKey.interestOps() & (~readOps));
        }

        /**
         * 注册事件
         * @param selectionKey
         * @param socketWrapper
         */
        public void reg(SelectionKey selectionKey,NioSocketWrapper socketWrapper, int intOps) {
            selectionKey.interestOps(intOps);
            socketWrapper.interestOps(intOps);
        }

        public Selector getSelector() {
            return selector;
        }
    }

    public class NioSocketWrapper extends SocketWrapperBase<NioChannel>{

        private NioChannel channel;


        private int interestOps = 0;

        public NioSocketWrapper(NioChannel channel) {
            this.channel = channel;
        }

        @Override
        public NioChannel getSocketChannel() {
            return channel;
        }

        public int interestOps(int interestOps) {
            this.interestOps = interestOps;
            return interestOps;
        }
    }

    public class NioSocketProcessor extends SocketProcessorBase<NioChannel> {

        public NioSocketProcessor(SocketWrapperBase<NioChannel> socketWrapper, SocketEvent event) {
            super(socketWrapper, event);
        }

        @Override
        protected void doRun() {
            getHandler().processor(socketWrapper,event);
        }
    }
}
