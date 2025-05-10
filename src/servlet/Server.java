package servlet;

import servlet.request.Request;
import servlet.request.RequestFacade;
import servlet.response.Response;
import servlet.response.ResponseFacade;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

public class Server {

    private static final Logger logger = Logger.getLogger("Server");
    private static final Map<String, Servlet> servletMapping = Map.of("/", new SimpleServlet());

    private static final int PORT = 8080;

    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
        try(ServerSocket serverSocket =  new ServerSocket(PORT)) {
            logger.info("Server is running on port " + PORT + "\n");

            while(true) {

                try(
                    Socket clientSocket = serverSocket.accept();
                    InputStream in = clientSocket.getInputStream();
                    OutputStream out = clientSocket.getOutputStream();
                ) {

                    String httpRequest = convertInputStreamToString(in);
                    if (httpRequest.isEmpty()) continue;
                    logger.info("HTTP Request: \n" + httpRequest + "\n");

                    /*
                     * [ Tomcat 에서의 HttpServletRequest 구현 과정 ]
                     * 1. 클라이언트에서 요청이 들어오면, Tomcat은 내부적으로 org.apache.coyote.Request 객체를 생성합니다.
                     * 2. Tomcat은 Request 객체를 ServletRequest 로 변환하는데, 이때 RequestFacade 객체가 생성합니다.
                     * 3. 최종적으로 서블릿에서 사용하는 HttpServletRequest 객체는 RequestFacade 인스턴스가 됩니다.
                     */
                    RequestFacade request = new RequestFacade(new Request(httpRequest));
                    ResponseFacade response = new ResponseFacade(new Response(out));

                    Servlet servlet = servletMapping.get(request.getRequestURI());
                    if (servlet != null) {
                        servlet.init();
                        servlet.service(request, response);
                        servlet.destroy();
                    } else {
                        String notFoundResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
                        out.write(notFoundResponse.getBytes());
                    }
                }

            }
        } catch (BindException e) {
            logger.info("Port " + PORT + " is already in use.\n" + e.getMessage());
        } catch (IOException e) {
            logger.info("An error occurred while running the servlet example server.\n" + e.getMessage());
        }
    }

    private static String convertInputStreamToString(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];

        int bytesRead;
        while((bytesRead = in.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, bytesRead));
            if(sb.toString().contains("\r\n\r\n")) break;
        }

        return sb.toString();
    }
}
