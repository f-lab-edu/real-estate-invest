package kancho.realestate.comparingprices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.SessionUserVO;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.exception.DuplicateLoginException;
import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	public static final String SESSION_KEY = "userNo";

	@PostMapping(value = "/join", produces = "application/json; charset=utf8")
	public ResponseEntity join(HttpServletRequest request, @RequestBody RequestUserDto requestUserDto) {
		HttpSession session = request.getSession();
		if(hasSessionKey(session)){
			throw new IllegalStateException("로그아웃 먼저 후 회원가입 해주세요.");
		}
		ResponseUserDto savedUser = userService.createUser(requestUserDto);
		return new ResponseEntity<>(new SuccessReponseDto<>("회원가입 완료", savedUser), HttpStatus.CREATED);
	}

	@PostMapping(value = "/login", produces = "application/json; charset=utf8")
	public ResponseEntity login(HttpServletRequest request, @RequestBody RequestUserDto requestUserDto) {
		User loginUser = userService.login(requestUserDto);
		HttpSession session = request.getSession();

		if(hasSessionKey(session)){
			validateDuplicateLogin((SessionUserVO)session.getAttribute(SESSION_KEY), requestUserDto);
			expirePreLoginSession(session);
		}

		/* User 다른 정보는 제외하고(특히 password) userNo와 id만 담은 SessionUserDto 사용 */
		SessionUserVO userDto = new SessionUserVO(loginUser.getUserNo(), loginUser.getId());
		session = request.getSession();
		session.setAttribute(SESSION_KEY, userDto);

		return new ResponseEntity<>(new SuccessReponseDto<>("로그인 성공", ""), HttpStatus.CREATED);
	}

	@GetMapping(value = "/health-check", produces = "application/json; charset=utf8")
	public ResponseEntity test(){
		return new ResponseEntity<>(new SuccessReponseDto<>("health-check",""), HttpStatus.OK);
	}

	private boolean hasSessionKey(HttpSession session) {
		return session.getAttribute(SESSION_KEY) != null;
	}

	private void validateDuplicateLogin(SessionUserVO userInSession, RequestUserDto requestUserDto) {
		if (userInSession.getId().equals(requestUserDto.getId())) {
			throw new DuplicateLoginException("이미 로그인한 상태입니다.");
		}
	}

	private void expirePreLoginSession(HttpSession session) {
		session.invalidate();
	}
}