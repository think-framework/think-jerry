package io.github.thinkframework.container;

import io.github.thinkframework.context.Lifecycle;
import io.github.thinkframework.context.LifecycleBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StandardServer extends LifecycleBase implements Server, Lifecycle {

    private static final Logger logger = LoggerFactory.getLogger(StandardServer.class);

    private String address = "localhost";
    private int port;

    private String shutdown;

    private List<Service> services;

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ServerSocket serverSocket;

    /**
     * 等待线程
     */
    private Thread awaitThread;

    @Override
    public void startInternal() {
        try{
            if (running) {
                return;
            }
            awaitThread = Thread.currentThread();
            if(services != null && !services.isEmpty()) {
                for (Service service : services) {
                    if (service instanceof Lifecycle) {
                        service.start();
                    }
                }
            }
            running = true;

            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(address, port));
//            executorService.execute(new Worker(serverSocket));// ip包,tcp包
            new Thread(new Worker(serverSocket),"Think-Jerry-Server Shutdown Thread")
                    .start(); // 关闭服务线程
        } catch (IOException e) {
        } finally {
            logger.debug("服务器启动.");
        }
    }

    public void await() {
        awaitThread = Thread.currentThread();
        try {
            while (running) {
                Thread.sleep(1000l); // 死循环, 暂停1s
            }
        } catch (InterruptedException e) {
        } finally {
            stop();
            logger.debug("服务器关闭.");
        }

    }

    @Override
    public void stopInternal() {
        if (running) {
            return;
        }
        if(services != null && !services.isEmpty()) {
            for (Service service : services) {
                if (service instanceof Lifecycle) {
                    service.stop();
                }
            }
        }
//        executorService.shutdown();
    }

    class Worker implements Runnable {
        ServerSocket serverSocket;

        public Worker(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            // 阻塞，等待别人建立链接
            // 如果有一个客户端发起了tcp三次握手，尝试建立链接
            // 创建一个Socket，代表根客户端的的一个tcp链接
            try (Socket socket = serverSocket.accept();
                 InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                byte[] lenght = new byte[4];
                int l = inputStream.read(lenght);
                ByteBuffer byteBuffer = ByteBuffer.wrap(lenght);
                int len = byteBuffer.getInt();

                byte[] request = new byte[len];
                for (int total = inputStream.read(request);
                     total > -1;
                     request = new byte[10], total = inputStream.read(request,0,total)) {
                    if (socket.isClosed()) {
                        break;
                    }
                    byte[] reponse = request;
                    outputStream.write(reponse);// ip包,tcp包
                    outputStream.flush();
                    logger.info("reponse: {}", new String(reponse));
                }
            } catch (IOException e) {
                logger.error("", e);
            } finally {
                running = false; // 状态变更, 关闭服务器
                awaitThread.interrupt();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getShutdown() {
        return shutdown;
    }

    public void setShutdown(String shutdown) {
        this.shutdown = shutdown;
    }

    public List<Service> getServices() {
        return services;
    }

    public boolean addService(Service service) {
        return services.add(service);
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
