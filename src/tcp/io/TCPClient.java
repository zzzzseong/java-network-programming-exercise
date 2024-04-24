package tcp.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class TCPClient {

    private static final Logger logger = Logger.getLogger(TCPClient.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try(
            Socket clientSocket = new Socket(HOST, PORT);
            OutputStream os = clientSocket.getOutputStream();
            InputStream is = clientSocket.getInputStream()
        ) {
            logger.info("Client is connected to server[" + HOST + ":" + PORT + "]\n");

            os.write("Hello, Server!".getBytes());
            os.flush();

            byte[] readByte = new byte[1024];
            int readByteLen = is.read(readByte);

            logger.info("Server sent message: " + new String(readByte, 0, readByteLen) + "\n");
        } catch (UnknownHostException e) {
            logger.info("UnknownHostException: 알 수 없는 호스트[" + HOST + "]입니다.\n" + e.getMessage());
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }
}