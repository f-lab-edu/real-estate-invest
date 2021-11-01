package kancho.realestate.comparingprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ServletComponentScan
public class ComparingpricesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparingpricesApplication.class, args);
	}

	// // 사용자 패스워드 암호화용 Bean
	// @Bean
	// public BCryptPasswordEncoder passwordEncoder(){
	// 	return new BCryptPasswordEncoder();
	// }
}
