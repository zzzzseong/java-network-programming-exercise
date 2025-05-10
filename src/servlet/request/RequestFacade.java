package servlet.request;

/**
 * RequestFacade는 HttpServletRequest 인터페이스를 구현한 클래스야.
 * 이 클래스는 실제 요청 데이터를 담고 있는 Request 객체에 대한 접근을 제한하는 역할을 해.
 * 	•	RequestFacade는 HttpServletRequest의 메서드들을 모두 구현하고,
 * 실제 작업은 Request 객체에 위임하는 구조로 설계돼.
 *
 * 이 구조를 통해 서블릿이 직접적으로 Tomcat 내부 객체에 접근하지 못하도록 보호할 수 있어.
 * 즉, 캡슐화와 보안성을 높이는 목적이야.
 * */
public class RequestFacade implements HttpServletRequest {

    protected Request request;

    public RequestFacade(Request request) {
        this.request = request;
    }

    @Override
    public String getMethod() {
        return request.getMethod();
    }

    @Override
    public String getRequestURI() {
        return request.getRequestURI();
    }
}
