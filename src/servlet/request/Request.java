package servlet.request;

import java.io.IOException;

public class Request implements HttpServletRequest {

    private final String method;
    private final String requestURI;

    public Request(String request) throws IOException {
        String[] parts = request.split(" ");
        method = parts[0];
        requestURI = parts[1];
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

}
