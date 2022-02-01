package kancho.realestate.comparingprices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
public class MysqlSessionConfiguration {

	@Bean
	public SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry(
		JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
		return new SpringSessionBackedSessionRegistry<>(jdbcIndexedSessionRepository);
	}
}
