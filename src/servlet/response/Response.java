package servlet.response;

import java.io.IOException;
import java.io.OutputStream;

public class Response implements HttpServletResponse {

    private final OutputStream outputStream;
    private int statusCode = SC_OK;
    private String contentType = null;


    public Response(OutputStream out) {
        this.outputStream = out;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void setContentType(String type) {
        this.contentType = type;

    }

    @Override
    public void setStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void write(String body) throws IOException {
        String statusLine = switch(statusCode) {
            case SC_OK -> "HTTP/1.1 200 OK";
            case SC_NOT_FOUND -> "HTTP/1.1 404 Not Found";
            default -> "HTTP/1.1 500 Internal Server Error";
        };

        String response = statusLine + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + body.getBytes().length + "\r\n" +
                "\r\n" +
                body;

        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}
