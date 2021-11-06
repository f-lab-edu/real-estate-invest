package kancho.realestate.comparingprices.service;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.UserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.exception.DuplicateUserAccountException;
import kancho.realestate.comparingprices.exception.InvalidLoginParameterException;
import kancho.realestate.comparingprices.repository.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	// private final BCryptPasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Transactional
	public void createUser(UserDto requestUser) {
		// String encryptedPw=passwordEncoder.encode(requestUser.getPassword());
		validateNotExistUser(getUserById(requestUser.getId()));
		String encryptedPw= BCrypt.hashpw(requestUser.getPassword(),BCrypt.gensalt());
		User user = User.createUser(requestUser.getId(),encryptedPw);
		userMapper.insertUser(user);
	}

	private void validateNotExistUser(Optional<User> foundUser) {
		if(isExistUser(foundUser)){
			throw new DuplicateUserAccountException("이미 사용중인 아이디입니다.");
		}
	}


	public User login(UserDto requestUser){
		Optional<User> foundUser= getUserById(requestUser.getId());
		validateExistUser(foundUser);
		User user = foundUser.get();
		validatePassword(requestUser.getPassword(), user.getPassword());
		return user;
	}

	private void validateExistUser(Optional<User> foundUser) {
		if(!isExistUser(foundUser)){
			throw new InvalidLoginParameterException("없는 아이디 입니다.");
		}
	}

	private boolean isExistUser(Optional<User> user){
		if(user.isPresent()){
			return true;
		}
		return false;
	}

	private void validatePassword(String inputPassword, String storedPassword) {
		// if(!passwordEncoder.matches(inputPassword, storedPassword)){
		if(!BCrypt.checkpw(inputPassword, storedPassword)){
			throw new InvalidLoginParameterException("비밀번호가 틀렸습니다.");
		}
	}

	public Optional<User> getUserById(String id){
		Optional<User> foundUser= userMapper.selectUserById(id);
		return foundUser;
	}
}
