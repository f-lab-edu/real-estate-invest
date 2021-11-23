package kancho.realestate.comparingprices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kancho.realestate.comparingprices.interceptor.AuthenticationInterceptor;
import kancho.realestate.comparingprices.interceptor.LoggingInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationInterceptor())
			.addPathPatterns("/my-estate/*");

		registry.addInterceptor(new LoggingInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/health-check");
	}
}
