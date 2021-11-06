package kancho.realestate.comparingprices.controller;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.request.UserDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponse;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	public static final String SESSION_KEY="userNo";

	@PostMapping(value = "/join", produces = "application/json; charset=utf8")
	public ResponseEntity join(@RequestBody UserDto requestUserDto){
		userService.createUser(requestUserDto);
		return new ResponseEntity<>(new SuccessReponse<>("회원가입 완료",""), HttpStatus.CREATED);
	}

	@PostMapping(value ="/login", produces = "application/json; charset=utf8")
	public ResponseEntity login(HttpSession session, @RequestBody UserDto requestUserDto){
		User loginUser = userService.login(requestUserDto);
		session.setAttribute(SESSION_KEY,loginUser.getUserNo());
		return new ResponseEntity<>(new SuccessReponse<>("회원가입 완료",""), HttpStatus.CREATED);
	}
}