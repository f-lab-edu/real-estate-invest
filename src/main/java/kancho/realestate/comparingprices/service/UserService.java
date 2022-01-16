package kancho.realestate.comparingprices.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.exception.DuplicateUserAccountException;
import kancho.realestate.comparingprices.exception.IdNotExistedException;
import kancho.realestate.comparingprices.exception.PasswordWrongException;
import kancho.realestate.comparingprices.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public ResponseUserDto createUser(RequestUserDto requestUser) {
		validateNotExistUser(getUserById(requestUser.getAccount()));
		String encryptedPw = getEncryptedPassword(requestUser.getPassword());
		User user = new User(requestUser.getAccount(), encryptedPw);
		userRepository.save(user);

		return ResponseUserDto.from(user);
	}

	public String getEncryptedPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	private void validateNotExistUser(Optional<User> foundUser) {
		if (isExistUser(foundUser)) {
			throw new DuplicateUserAccountException("이미 사용중인 아이디입니다.");
		}
	}

	public User login(RequestUserDto requestUser) {
		User foundUser = getUserById(requestUser.getAccount()).orElseThrow(() -> new IdNotExistedException());
		validatePassword(requestUser.getPassword(), foundUser.getPassword());
		foundUser.updateLastLoginDttm();
		return foundUser;
	}

	private boolean isExistUser(Optional<User> user) {
		return user.isPresent();
	}

	private void validatePassword(String inputPassword, String storedPassword) {
		if (!BCrypt.checkpw(inputPassword, storedPassword)) {
			throw new PasswordWrongException();
		}
	}

	public Optional<User> getUserById(String account) {
		Optional<User> foundUser = userRepository.findByAccount(account);
		return foundUser;
	}
}
