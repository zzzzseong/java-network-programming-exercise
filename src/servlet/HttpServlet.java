package servlet;

import servlet.request.HttpServletRequest;
import servlet.request.ServletRequest;
import servlet.response.HttpServletResponse;
import servlet.response.ServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class HttpServlet implements Servlet {

    private final Logger logger = Logger.getLogger("HttpServlet");

    private static final String METHOD_POST   = "POST";
    private static final String METHOD_GET    = "GET";
    private static final String METHOD_PUT    = "PUT";
    private static final String METHOD_DELETE = "DELETE";

    @Override
    public void init() {
        logger.info("Servlet init");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        service((HttpServletRequest) req, (HttpServletResponse) res);
    }
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String method = req.getMethod();

        switch (method) {
            case METHOD_GET -> doGet(req, res);
            case METHOD_POST -> doPost(req, res);
            case METHOD_PUT -> doPut(req, res);
            case METHOD_DELETE -> doDelete(req, res);
            default -> setNotImplementedResponse(res);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setNotImplementedResponse(res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setNotImplementedResponse(res);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setNotImplementedResponse(res);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        setNotImplementedResponse(res);
    }

    @Override
    public void destroy() {
        logger.info("Servlet destroy");
    }

    private void setNotImplementedResponse(HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
        res.setContentType("text/plain");
        res.write("Not Implemented");
    }

}
