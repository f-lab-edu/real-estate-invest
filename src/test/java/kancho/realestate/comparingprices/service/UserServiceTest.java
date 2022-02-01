package kancho.realestate.comparingprices.service;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.exception.DuplicateUserAccountException;

class UserServiceTest extends ServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ParameterizedTest
	@CsvSource(value = {"tom2542,23lg354232"})
	void 회원가입_패스워드_암호화_테스트(String id, String password) {
		//given
		RequestUserDto requestUserDto = new RequestUserDto(id, password);

		//when
		userService.createUser(requestUserDto);
		User createdUser = userService.findUserByAccount(id);

		//then
		Assertions.assertThat(createdUser).isNotEqualTo(password);
		Assertions.assertThat(bCryptPasswordEncoder.matches(requestUserDto.getPassword(), createdUser.getPassword()))
			.isTrue();
	}

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 회원가입_중복아이디_오류(String id, String password) {
		//given
		RequestUserDto requestJoinUserDto = new RequestUserDto(id, password);
		userService.createUser(requestJoinUserDto);

		//when, then
		RequestUserDto requestDuplicateJoinUserDto = new RequestUserDto(id, password);
		Assertions.assertThatThrownBy(() -> userService.createUser(requestDuplicateJoinUserDto))
			.isInstanceOf(DuplicateUserAccountException.class);
	}

	@ParameterizedTest
	@CsvSource(value = {"tom2542:23lg354232"}, delimiter = ':')
	void 로그인_후_최종로그인시각_변경_확인(String id, String password) {
		//given
		RequestUserDto requestJoinUserDto = new RequestUserDto(id, password);
		ResponseUserDto user = userService.createUser(requestJoinUserDto);

		//when
		LocalDateTime loginTime = LocalDateTime.now();
		userService.changeLoginTime(user.getUserId(), loginTime);

		//then
		User findUser = userService.findUser(user.getUserId());
		Assertions.assertThat(findUser.getLastLoginDttm()).isEqualTo(loginTime);
	}
}
