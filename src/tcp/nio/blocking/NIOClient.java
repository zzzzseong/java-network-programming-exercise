package tcp.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class NIOClient {

    private static final Logger logger = Logger.getLogger(NIOClient.class.getName());
    private static final Charset charset = StandardCharsets.UTF_8;

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            int fi = i;
            Thread thread = new Thread(() -> run("message" + fi));
            thread.start();
        }
    }

    public static void run(String message) {
        try(SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(true);

            socketChannel.connect(new InetSocketAddress(HOST, PORT));

            ByteBuffer buffer = charset.encode(message);
            socketChannel.write(buffer);

            // 응답 데이터 수신
            buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            buffer.flip();
            logger.info("Server sent message: " + charset.decode(buffer));
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }
}
