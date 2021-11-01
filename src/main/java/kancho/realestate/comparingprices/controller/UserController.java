package kancho.realestate.comparingprices.controller;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import kancho.realestate.comparingprices.domain.dto.request.UserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	public static final String SESSION_KEY="userNo";

	@PostMapping("/join")
	public String join(@RequestBody UserDto requestUserDto){
		userService.createUser(requestUserDto);
		return "회원가입 완료";
	}

	@PostMapping("/login")
	public String login(HttpSession session, @RequestBody UserDto requestUserDto){
		User loginUser = userService.login(requestUserDto);
		session.setAttribute(SESSION_KEY,loginUser.getUserNo());
		return "로그인 완료";
	}

	@GetMapping("/test")
	public String test(){
		return "test";
	}
}
