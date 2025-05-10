package servlet.response;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseFacade implements HttpServletResponse {

    protected Response response;

    public ResponseFacade(Response response) {
        this.response = response;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    @Override
    public void setContentType(String type) {
        response.setContentType(type);
    }

    @Override
    public void setStatus(int statusCode) {
        response.setStatus(statusCode);
    }

    @Override
    public void write(String body) throws IOException {
        response.write(body);
    }
}
