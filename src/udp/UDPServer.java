package udp;

import java.io.IOException;
import java.net.*;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * UDP 통신 실습을 위한 MultiThread EchoServer Example (Thread : Client = 1: 1)
 * Java IO는 기본적으로 Blocking I/O이기 때문에 Non-Blocking I/O 기반 서버를 구축하기 위해서는 NIO를 사용해야 한다.
 * */
public class UDPServer {

    private static final Logger logger = Logger.getLogger(UDPServer.class.getName());

    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 3;

    public static void main(String[] args) throws SocketException {
        startUDPServer();
    }

    public static void startUDPServer() {
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            logger.info("UDP Server is running on port " + PORT + "\n");

            while(true) {
                DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                socket.receive(receivePacket);
                threadPool.execute(() -> processTask(socket, receivePacket, UUID.randomUUID()));
            }
        } catch (SocketException e) {
            logger.info("SocketException: " + e.getMessage());
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }

    public static void processTask(
            DatagramSocket socket,
            DatagramPacket receivePacket,
            UUID uuid
    ) {
        try {
            SocketAddress address = receivePacket.getSocketAddress();
            logger.info("Thread[" + uuid + "] is processing by Client[" + address + "]\n");
            logger.info("Client[" + address + "] sent message: " + new String(receivePacket.getData(), 0, receivePacket.getLength()) + "\n");

            DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getLength(), address);
            socket.send(sendPacket);
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }
}
