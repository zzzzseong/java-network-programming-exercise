package servlet.request;

/**
 * 서블릿 컨테이너(예: Tomcat, Jetty, Undertow)는 각각의 구현체를 가지고 있습니다.
 * 따라서, HttpServletRequest 인터페이스의 구현체는 서블릿 컨테이너에 따라 달라집니다.
 *
 * 예를 들어:
 * 	1.	Tomcat:
 * 	•	org.apache.catalina.connector.RequestFacade
 * 	•	org.apache.catalina.connector.Request
 * 	2.	Jetty:
 * 	•	org.eclipse.jetty.server.Request
 * 	3.	Undertow:
 * 	•	io.undertow.servlet.spec.HttpServletRequestImpl
 * */
public interface HttpServletRequest extends ServletRequest {
    String getMethod();
    String getRequestURI();
}
