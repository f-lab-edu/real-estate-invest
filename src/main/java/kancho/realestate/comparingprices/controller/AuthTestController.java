package kancho.realestate.comparingprices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthTestController {

	@GetMapping("/test")
	public String test(){
		return "auth test";
	}
}
