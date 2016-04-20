package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for Cross-Origin Resource Sharing.
 *
 */
@WebFilter(urlPatterns = {"/*"}, description = "CORS Filter")
public class CORSFilter implements Filter {

	private void log(String msg) {
		System.out.println(msg);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log("CORSFilter : init()");
	}

	@Override
	public void destroy() {
		log("CORSFilter : destroy()");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		log("CORSFilter : doFilter(" + request.getMethod() +" '" + request.getRequestURI() + "')");
		HttpServletResponse response = (HttpServletResponse) res;
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
//		response.setHeader("Access-Control-Max-Age", "1800");
		
		logHeader(request, "Origin");
		logHeader(request, "Access-Control-Request-Headers");
		logHeader(request, "Access-Control-Request-Method");
		
		//--- Set response header
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, HEAD, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Response-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        // NB : add "Response-Type" in the list. Due to XHR.setRequestHeader("response-type", sType );
		chain.doFilter(req, res);
	}

	private void logHeader(HttpServletRequest request, String headerName) {
		log(" . '" + headerName + "' = '" + request.getHeader(headerName) + "'") ;
	}
	
}