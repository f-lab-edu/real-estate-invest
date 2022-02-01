package kancho.realestate.comparingprices.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
		validateNotExistUser(findUserByAccountOptional(requestUser.getAccount()));
		String encryptedPw = getEncryptedPassword(requestUser.getPassword());
		User user = User.makeBasicAuthUser(requestUser.getAccount(), encryptedPw);
		userRepository.save(user);

		return ResponseUserDto.from(user);
	}

	@Transactional
	public User oAuth2Login(User oAuth2User) {
		oAuth2User.updateLastLoginDttm(LocalDateTime.now());
		return userRepository.save(oAuth2User);
	}

	@Transactional
	public void changeLoginTime(Long userId, LocalDateTime loginTime){
		User findUser = userRepository.findById(userId).orElseThrow(IdNotExistedException::new);
		findUser.updateLastLoginDttm(loginTime);
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

	public User findUserByAccount(String account) {
		return findUserByAccountOptional(account)
			.orElseThrow(IdNotExistedException::new);
	}

	protected User findUser(Long userId){
		return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
	}

	public Optional<User> findUserByAccountOptional(String account) {
		return userRepository.findByAccount(account);
	}

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		User foundUser = findUserByAccount(account);
		return new SessionUserVO(foundUser.getAccount(),foundUser.getPassword(),new ArrayList<>(),foundUser.getId());
	}


}
