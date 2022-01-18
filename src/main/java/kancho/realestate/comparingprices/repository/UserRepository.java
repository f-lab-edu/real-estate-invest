package kancho.realestate.comparingprices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kancho.realestate.comparingprices.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByAccount(String account);
}
