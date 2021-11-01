package kancho.realestate.comparingprices.filter;


import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kancho.realestate.comparingprices.controller.UserController;

@WebFilter(urlPatterns = "/auth/*")
// @WebFilter
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// User 세션에 값이 있으면
		if(session.getAttribute(UserController.SESSION_KEY) == null){
			throw new AuthenticationException("로그인이 필요합니다.");
		}

		chain.doFilter(req, res);
		return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("authenticateFilter init");
	}

	@Override
	public void destroy() {
		System.out.println("authenticateFilter destroyed");
	}
}
