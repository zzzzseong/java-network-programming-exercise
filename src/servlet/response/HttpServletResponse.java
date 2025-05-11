package servlet.response;

import java.io.IOException;

public interface HttpServletResponse extends ServletResponse {

    int SC_OK = 200;
    int SC_NOT_FOUND = 404;
    int SC_INTERNAL_SERVER_ERROR = 500;
    int SC_NOT_IMPLEMENTED = 501;

}
