package servlet.response;

import java.io.IOException;
import java.io.OutputStream;

public interface ServletResponse {
    OutputStream getOutputStream() throws IOException;
    void setContentType(String type);
    void setStatus(int statusCode);
    void write(String body) throws IOException;
}
