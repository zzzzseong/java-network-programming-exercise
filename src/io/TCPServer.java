package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * TCP 통신 실습을 위한 MultiThread EchoServer Example (Thread : Client = 1: 1)
 * Java IO는 기본적으로 Blocking I/O이기 때문에 Non-Blocking I/O 기반 서버를 구축하기 위해서는 NIO를 사용해야 한다.
 * */
public class TCPServer {

    private static final Logger logger = Logger.getLogger(TCPServer.class.getName());

    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 3;

    public static void main(String[] args) {
        startTCPServer();
    }

    public static void startTCPServer() {
        try(
            ServerSocket serverSocket = new ServerSocket(PORT);
            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE)
        ) {
            logger.info("TCP Server is running on port " + PORT + "\n");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                InetSocketAddress address = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                logger.info("Client[" + address.getHostName() + "] is connected\n");
                threadPool.execute(() -> processTask(clientSocket, address, UUID.randomUUID()));
            }
        } catch (BindException e) {
            logger.info("BindException: 이미 OS에서 사용중인 포트[" + PORT + "]입니다.\n" + e.getMessage());
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }

    public static void processTask(Socket clientSocket, InetSocketAddress address, UUID uuid) {
        try(
            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream()
        ) {
            logger.info("Thread[" + uuid + "] is processing by Client[" + address.getHostName() + "]\n");

            byte[] readByte = new byte[1024];
            int readByteLen = is.read(readByte);

            logger.info("Client[" + address.getHostName() + "] sent message: " + new String(readByte, 0, readByteLen) + "\n");

            os.write(readByte, 0, readByteLen);
            os.flush();

            logger.info("Client[" + address.getHostName() + "] is disconnected\n");
            clientSocket.close();
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }
}