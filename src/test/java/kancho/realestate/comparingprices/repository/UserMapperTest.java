package kancho.realestate.comparingprices.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.model.User;

@SpringBootTest
@Transactional
@Rollback(false)
class UserMapperTest {

	@Autowired
	UserMapper userMapper;

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 사용자_등록(String id, String password) {
		User user = User.createUser(id,password);
		userMapper.insertUser(user);
		Optional<User> createdUser = userMapper.selectUserById(id);
		Assertions.assertThat(createdUser.isPresent()).isEqualTo(true);
		Assertions.assertThat(createdUser.get().getId()).isEqualTo(id);
	}

}