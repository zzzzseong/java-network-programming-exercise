package servlet;

import servlet.request.HttpServletRequest;
import servlet.response.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("text/plain");
        res.write("요청이 성공적으로 처리되었습니다.");
    }
}
