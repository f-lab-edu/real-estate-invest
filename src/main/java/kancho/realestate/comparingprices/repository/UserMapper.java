package kancho.realestate.comparingprices.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.User;

@Mapper
public interface UserMapper {

	void insertUser(User user);
	Optional<User> selectUserById(String id);
}
