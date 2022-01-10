package kancho.realestate.utils.api;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StorePricesMain {

	public static void main(String[] args){
		new SpringApplicationBuilder(StorePricesMain.class).web(WebApplicationType.NONE).run(args);
	}
}
