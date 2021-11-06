package kancho.realestate.comparingprices.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.exception.DuplicateUserAccountException;
import kancho.realestate.comparingprices.exception.InvalidLoginParameterException;
import kancho.realestate.comparingprices.repository.UserMapper;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

	@Transactional
	@ParameterizedTest
	@CsvSource(value = {"tom2542,23lg354232"})
	void 회원가입_패스워드_암호화_테스트(String id, String password) {
		RequestUserDto requestUserDto = new RequestUserDto(id, password);
		userService.createUser(requestUserDto);
		Optional<User> createdUser = userService.getUserById(id);
		Assertions.assertThat(createdUser.orElse(null)).isNotEqualTo(password);
	}

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 로그인_테스트(String id, String password) {
		RequestUserDto requestUserDto = new RequestUserDto(id, password);
		userService.createUser(requestUserDto);
		userService.login(requestUserDto);
	}

	@Transactional
	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232:23lg35423"}, delimiter = ':')
	void 로그인_비밀번호_오류_테스트(String id, String password, String wrongPassword) {
		RequestUserDto requestJoinUserDto = new RequestUserDto(id, password);
		userService.createUser(requestJoinUserDto);

		RequestUserDto requestLoginUserDto = new RequestUserDto(id, wrongPassword);
		Assertions.assertThatThrownBy(()->{
			userService.login(requestLoginUserDto);
		}).isInstanceOf(InvalidLoginParameterException.class);

	}

	@Transactional
	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232:tom25"}, delimiter = ':')
	void 로그인_없는_아이디_오류_테스트(String id, String password, String wrongId) {
		RequestUserDto requestJoinUserDto = new RequestUserDto(id, password);
		userService.createUser(requestJoinUserDto);

		RequestUserDto requestLoginUserDto = new RequestUserDto(wrongId, password);
		Assertions.assertThatThrownBy(()->{
			userService.login(requestLoginUserDto);
		}).isInstanceOf(InvalidLoginParameterException.class);
	}

	@Transactional
	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 회원가입_중복아이디_오류(String id, String password){
		RequestUserDto requestJoinUserDto = new RequestUserDto(id, password);
		userService.createUser(requestJoinUserDto);

		RequestUserDto requestDuplicateJoinUserDto = new RequestUserDto(id, password);
		Assertions.assertThatThrownBy(()->{
			userService.createUser(requestDuplicateJoinUserDto);
		}).isInstanceOf(DuplicateUserAccountException.class);

	}
}