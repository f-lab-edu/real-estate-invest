package kancho.realestate.comparingprices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;

@RestController
@RequestMapping("/my-estate")
public class UserEstateController {

	@GetMapping(value = "/test", produces = "application/json; charset=utf8")
	public ResponseEntity test(){
		return new ResponseEntity<>(new SuccessReponseDto<>("세션 체크 테스트",""), HttpStatus.OK);
	}
}
