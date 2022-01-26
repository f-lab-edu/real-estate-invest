package kancho.realestate.comparingprices.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.model.User;

class UserRepositoryTest extends DomainTest {

	@Autowired
	private UserRepository userRepository;

	@ParameterizedTest
	@Transactional
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 사용자_등록(String id, String password) {

		// when
		User user = new User(id, password);
		userRepository.save(user);

		// then
		assertThat(user.getId()).isNotNull();
	}

	@DisplayName("등록된 사용자 조회")
	@Test
	void selectUserById() {
		// given
		String account ="testid123";
		String password = "test password1234";
		User user = new User(account, password);
		userRepository.save(user);

		// when
		Optional<User> createdUser = userRepository.findByAccount(account);
		assertThat(createdUser.isPresent()).isEqualTo(true);
		assertThat(createdUser.get().getId()).isEqualTo(user.getId());
		assertThat(createdUser.get().getAccount()).isEqualTo(account);
	}

}
