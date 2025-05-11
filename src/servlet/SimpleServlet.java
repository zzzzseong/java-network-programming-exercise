package servlet;

import servlet.request.HttpServletRequest;
import servlet.response.HttpServletResponse;

import java.io.IOException;

public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("text/plain");
        res.write("GET 요청이 성공적으로 처리되었습니다.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("text/plain");
        res.write("POST 요청이 성공적으로 처리되었습니다.");
    }
}