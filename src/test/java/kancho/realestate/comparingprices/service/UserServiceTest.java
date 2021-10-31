package kancho.realestate.comparingprices.service;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.UserDto;
import kancho.realestate.comparingprices.domain.model.User;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	UserService userService;

	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 회원가입_패스워드_암호화_테스트(String id, String password) {
		UserDto requestUserDto = new UserDto(id, password);
		userService.createUser(requestUserDto);
		User createdUser = userService.getUserById(id);
		Assertions.assertThat(createdUser.getPassword()).isNotEqualTo(password);
	}

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 로그인_테스트(String id, String password) {
		UserDto requestUserDto = new UserDto(id, password);
		userService.createUser(requestUserDto);
		userService.login(requestUserDto);
	}

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232:23lg35423"}, delimiter = ':')
	void 로그인_비밀번호_오류_테스트(String id, String password, String wrongPassword) {
		UserDto requestJoinUserDto = new UserDto(id, password);
		userService.createUser(requestJoinUserDto);

		UserDto requestLoginUserDto = new UserDto(id, wrongPassword);
		Assertions.assertThatThrownBy(()->{
			userService.login(requestLoginUserDto);
		}).isInstanceOf(InvalidParameterException.class);

	}

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232:tom25"}, delimiter = ':')
	void 로그인_없는_아이디_오류_테스트(String id, String password, String wrongId) {
		UserDto requestJoinUserDto = new UserDto(id, password);
		userService.createUser(requestJoinUserDto);

		UserDto requestLoginUserDto = new UserDto(wrongId, password);
		Assertions.assertThatThrownBy(()->{
			userService.login(requestLoginUserDto);
		}).isInstanceOf(InvalidParameterException.class);

	}
}