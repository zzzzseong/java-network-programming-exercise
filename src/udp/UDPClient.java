package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class UDPClient {

    private static final Logger logger = Logger.getLogger(UDPClient.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket()) {
            byte[] bytes = "Hello, Server!".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, new InetSocketAddress(HOST, PORT));

            socket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(receivePacket);
            logger.info("Server sent message: " + new String(receivePacket.getData(), 0, receivePacket.getLength()) + "\n");
        } catch (SocketException e) {
            logger.info("SocketException: " + e.getMessage());
        } catch (IOException e) {
            logger.info("IOException: " + e.getMessage());
        }
    }
}
