package kancho.realestate.comparingprices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;

import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=utf8")
public class UserController {

	private final UserService userService;

	@PostMapping(value = "/join", produces = "application/json; charset=utf8")
	public ResponseEntity join(@RequestBody RequestUserDto requestUserDto) {

		ResponseUserDto savedUser = userService.createUser(requestUserDto);
		return new ResponseEntity<>(new SuccessReponseDto<>("회원가입 완료", savedUser), HttpStatus.CREATED);
	}

	@GetMapping(value = "/health-check", produces = "application/json; charset=utf8")
	public ResponseEntity test(){
		return new ResponseEntity<>(new SuccessReponseDto<>("health-check",""), HttpStatus.OK);
	}

}
