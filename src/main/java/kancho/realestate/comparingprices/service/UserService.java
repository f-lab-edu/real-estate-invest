package kancho.realestate.comparingprices.service;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.UserDto;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.repository.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Transactional
	public void createUser(UserDto requestUser) {
		String encryptedPw=passwordEncoder.encode(requestUser.getPassword());
		User user = User.createUser(requestUser.getId(),encryptedPw);
		userMapper.insertUser(user);
	}

	public void login(UserDto requestUser){
		User foundUser= getUserById(requestUser.getId());

		validatePassword(requestUser.getPassword(), foundUser.getPassword());

	}

	private void validatePassword(String inputPassword, String storedPassword) {
		if(!passwordEncoder.matches(inputPassword, storedPassword)){
			throw new InvalidParameterException("비밀번호가 틀렸습니다.");
		}
	}

	public User getUserById(String id){
		Optional<User> foundUser= userMapper.selectUserById(id);
		if(foundUser.isEmpty()){
			throw new InvalidParameterException("없는 아이디 입니다.");
		}
		return foundUser.get();
	}
}
