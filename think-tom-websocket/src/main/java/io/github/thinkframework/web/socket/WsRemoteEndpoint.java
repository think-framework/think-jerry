package io.github.thinkframework.web.socket;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;

public class WsRemoteEndpoint implements RemoteEndpoint {

    private boolean batchingAllowed;
    @Override
    public void setBatchingAllowed(boolean batchingAllowed) throws IOException {
        this.batchingAllowed = batchingAllowed;
    }

    @Override
    public boolean getBatchingAllowed() {
        return batchingAllowed;
    }

    @Override
    public void flushBatch() throws IOException {

    }

    @Override
    public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

    }

    @Override
    public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

    }

     class Async implements RemoteEndpoint.Async {

         @Override
         public long getSendTimeout() {
             return 0;
         }

         @Override
         public void setSendTimeout(long timeoutmillis) {

         }

         @Override
         public void sendText(String text, SendHandler handler) {

         }

         @Override
         public Future<Void> sendText(String text) {
             return null;
         }

         @Override
         public Future<Void> sendBinary(ByteBuffer data) {
             return null;
         }

         @Override
         public void sendBinary(ByteBuffer data, SendHandler handler) {

         }

         @Override
         public Future<Void> sendObject(Object data) {
             return null;
         }

         @Override
         public void sendObject(Object data, SendHandler handler) {

         }

         @Override
         public void setBatchingAllowed(boolean allowed) throws IOException {

         }

         @Override
         public boolean getBatchingAllowed() {
             return false;
         }

         @Override
         public void flushBatch() throws IOException {

         }

         @Override
         public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

         }

         @Override
         public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

         }
     }

    class Basic implements RemoteEndpoint.Basic {

        @Override
        public void sendText(String text) throws IOException {

        }

        @Override
        public void sendBinary(ByteBuffer data) throws IOException {

        }

        @Override
        public void sendText(String partialMessage, boolean isLast) throws IOException {

        }

        @Override
        public void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException {

        }

        @Override
        public OutputStream getSendStream() throws IOException {
            return null;
        }

        @Override
        public Writer getSendWriter() throws IOException {
            return null;
        }

        @Override
        public void sendObject(Object data) throws IOException, EncodeException {

        }

        @Override
        public void setBatchingAllowed(boolean allowed) throws IOException {

        }

        @Override
        public boolean getBatchingAllowed() {
            return false;
        }

        @Override
        public void flushBatch() throws IOException {

        }

        @Override
        public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

        }

        @Override
        public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

        }
    }
}
