package kancho.realestate.utils.api;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"kancho.realestate"})
public class StorePricesMain {

	public static void main(String[] args){
		// Spring Security OAuth2 설정으로 초기화시 Servlet 빈을 요구하여서 WebApplicationType.SERVLET 으로 사용.
		// 모듈 분리 시에는 다시 WebApplicationType.NONE 으로 사용.
		new SpringApplicationBuilder(StorePricesMain.class).web(WebApplicationType.SERVLET).run(args);
	}
}
