package tcp.nio.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class NIOBlockingServer {

    private static final Logger logger = Logger.getLogger(NIOBlockingServer.class.getName());
    private static final Charset charset = StandardCharsets.UTF_8;

    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 3;

    public static void main(String[] args) {
        startNIOBlockingServer();
    }

    public static void startNIOBlockingServer() {
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            logger.info("NIO blocking server is running on port " + PORT + "\n");

            serverSocketChannel.configureBlocking(true);
            serverSocketChannel.bind(new InetSocketAddress(PORT));

            while(true) {
                SocketChannel clientSocketChannel = serverSocketChannel.accept();

                InetSocketAddress address = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
                logger.info("Client[" + address + "] is Connected\n");
                threadPool.execute(() -> processTask(clientSocketChannel, address, UUID.randomUUID()));
            }

        } catch(IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }

    public static void processTask(SocketChannel clientSocketChannel, InetSocketAddress address, UUID uuid) {
        try {
            logger.info("Thread[" + uuid + "] is processing by Client[" + address + "]\n");

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            clientSocketChannel.read(buffer);

            buffer.flip();
            String message = charset.decode(buffer).toString();
            logger.info("Client[" + address + "] sent message: " + message + "\n");

            buffer = charset.encode(message);
            clientSocketChannel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
