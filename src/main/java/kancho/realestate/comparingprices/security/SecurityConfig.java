package kancho.realestate.comparingprices.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import kancho.realestate.comparingprices.service.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션이 필요하면 생성하도록 셋팅
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false)
			.sessionRegistry(springSessionBackedSessionRegistry); // spring-session 과 연동하여 외부 저장소에 session 저장

		http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.authorizeRequests()
			.antMatchers("/my-estate/**")
			.authenticated();

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
		http.csrf().disable();
		http.httpBasic().disable();
		http.formLogin().disable();

		http.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.permitAll();
	}

	private Filter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(),
			userService);
		authenticationFilter.setAuthenticationSuccessHandler(appAuthenticationSuccessHandler());
		return authenticationFilter;
	}

	@Bean
	public AuthSuccessHandler appAuthenticationSuccessHandler(){
		return new AuthSuccessHandler();
	}

	@Bean
	public CustomeBasicAuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomeBasicAuthenticationEntryPoint();
	}

}
