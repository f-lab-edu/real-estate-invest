package kancho.realestate.comparingprices.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.exception.DuplicateLoginException;
import kancho.realestate.comparingprices.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final UserService userService;

	public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		super(authenticationManager);
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException, ServletException {
		try {
			super.doFilter(request, response, chain);
		} catch (DuplicateLoginException exception) {
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			RequestUserDto creds = new ObjectMapper().readValue(request.getInputStream(), RequestUserDto.class);

			HttpSession session = request.getSession(false);
			if (isAleadyLogin(session)) {
				validateDuplicateLogin(creds);
				expirePreLoginSession(session);
			}

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(creds.getAccount(), creds.getPassword())
			);
		} catch (DuplicateLoginException exception) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setCharacterEncoding("UTF-8");
			throw exception;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isAleadyLogin(HttpSession session) {
		return session != null;
	}

	private void validateDuplicateLogin(RequestUserDto creds) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;

		if (userDetails.getUsername().equals(creds.getAccount())) {
			throw new DuplicateLoginException("이미 로그인한 상태입니다.");
		}
	}

	private void expirePreLoginSession(HttpSession session) {
		session.invalidate();
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
