package kancho.realestate.utils.excel;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"kancho.realestate"})
public class StoreApartmentMain {

	public static void main(String[] args){
		new SpringApplicationBuilder(StoreApartmentMain.class).web(WebApplicationType.NONE).run(args);
	}
}
