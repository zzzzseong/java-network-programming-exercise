package servlet;

import servlet.request.ServletRequest;
import servlet.response.ServletResponse;

import java.io.IOException;

public interface Servlet {
    void init();
    void service(ServletRequest req, ServletResponse res) throws IOException;
    void destroy();
}
