package kancho.realestate.comparingprices.interceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kancho.realestate.comparingprices.controller.UserController;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		HttpSession session = request.getSession();

		// User 세션에 값이 있으면
		if (session.getAttribute(UserController.SESSION_KEY) == null) {
			throw new AuthenticationException("로그인이 필요합니다.");
		}

		return true;
	}
}
