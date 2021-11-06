package kancho.realestate.comparingprices.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.SessionUserVO;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponse;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.exception.DuplicateLoginException;
import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	public static final String SESSION_KEY="userNo";

	@PostMapping(value = "/join", produces = "application/json; charset=utf8")
	public ResponseEntity join(@RequestBody RequestUserDto requestUserDto){
		userService.createUser(requestUserDto);
		return new ResponseEntity<>(new SuccessReponse<>("회원가입 완료",""), HttpStatus.CREATED);
	}

	@PostMapping(value ="/login", produces = "application/json; charset=utf8")
	public ResponseEntity login(HttpSession session, @RequestBody RequestUserDto requestUserDto){
		if(session.getAttribute(SESSION_KEY)!=null){
			validateDuplicateLogin((SessionUserVO)session.getAttribute(SESSION_KEY),requestUserDto);
		}
		User loginUser = userService.login(requestUserDto);
		/* User 다른 정보는 제외하고(특히 password) userNo와 id만 담은 SessionUserDto 사용 */
		SessionUserVO userDto = new SessionUserVO(loginUser.getUserNo(),loginUser.getId());
		session.setAttribute(SESSION_KEY,userDto);
		return new ResponseEntity<>(new SuccessReponse<>("회원가입 완료",""), HttpStatus.CREATED);
	}

	private void validateDuplicateLogin(SessionUserVO userInSession, RequestUserDto requestUserDto) {
		if(userInSession.getId().equals(requestUserDto.getId())){
			throw new DuplicateLoginException("이미 로그인한 상태입니다.");
		}
	}
}