package kancho.realestate.comparingprices.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.request.UserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/test")
	public String test(){
		return "test";
	}

	@PostMapping("/join")
	public String join(@RequestBody UserDto requestUserDto){
		System.out.println("here inside");
		userService.createUser(requestUserDto);
		return "회원가입 완료";
	}

	@PostMapping("/login")
	public String login(HttpSession session, @RequestBody UserDto requestUserDto){
		User loginUser = userService.login(requestUserDto);
		session.setAttribute("userNo",loginUser.getUserNo());
		return "로그인 완료";
	}
}
