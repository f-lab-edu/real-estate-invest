package kancho.realestate.comparingprices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = "application/json; charset=utf8")
public class AuthController {


	@GetMapping(value = "/success", produces = "application/json; charset=utf8")
	public ResponseEntity loginSuceess(){
		return new ResponseEntity<>(new SuccessReponseDto<>("OAuth2 로그인 성공",""), HttpStatus.OK);
	}

	@GetMapping(value = "/fail", produces = "application/json; charset=utf8")
	public ResponseEntity loginFailure(){
		return new ResponseEntity<>(new SuccessReponseDto<>("OAuth2 로그인 실패",""), HttpStatus.OK);
	}
}
