//package io.github.thinkframework.connector.nio;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.Socket;
//import java.nio.channels.AsynchronousServerSocketChannel;
//import java.nio.channels.ServerSocketChannel;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class AsynchronousSocketChannelServer {
//
//    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//    public static void main(String[] args) throws IOException {
//        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
//        ServerSocketChannel serverSocket = ServerSocketChannel.open();
////        for(Socket socket =  serverSocket.accept(); socket!= null; socket = serverSocket.accept()) {
////            executorService.submit(new Worker(socket));
////        }
//    }
//    static class Worker implements Runnable {
//        Socket socket;
//
//        public Worker(Socket socket) {
//            this.socket = socket;
//        }
//
//        @Override
//        public void run() {
//            try (InputStream inputStream = socket.getInputStream()) {
////                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
////                BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
//                byte[] bytes = new byte[1024];
////                bufferedReader.readLine();
//                inputStream.read(bytes);
//                System.out.println(new String(bytes));
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//
//            }
//        }
//    }
//}
