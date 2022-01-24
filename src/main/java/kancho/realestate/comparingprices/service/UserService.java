package kancho.realestate.comparingprices.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.SessionUserVO;
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
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public ResponseUserDto createUser(RequestUserDto requestUser) {
		validateNotExistUser(getUserByAccountOptional(requestUser.getAccount()));
		String encryptedPw = getEncryptedPassword(requestUser.getPassword());
		User user = new User(requestUser.getAccount(), encryptedPw);
		userRepository.save(user);

		return ResponseUserDto.from(user);
	}

	private String getEncryptedPassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	private void validateNotExistUser(Optional<User> foundUser) {
		if (isExistUser(foundUser)) {
			throw new DuplicateUserAccountException("이미 사용중인 아이디입니다.");
		}
	}

	private boolean isExistUser(Optional<User> user) {
		return user.isPresent();
	}

	private void validatePassword(String inputPassword, String storedPassword) {
		if (!bCryptPasswordEncoder.matches(inputPassword, storedPassword)) {
			throw new PasswordWrongException();
		}
	}

	public User getUserByAccount(String account) {
		return getUserByAccountOptional(account)
			.orElseThrow(IdNotExistedException::new);
	}

	public Optional<User> getUserByAccountOptional(String account) {
		return userRepository.findByAccount(account);
	}

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		User foundUser = getUserByAccount(account);
		return new SessionUserVO(foundUser.getAccount(),foundUser.getPassword(),new ArrayList<>(),foundUser.getId());
	}
}
