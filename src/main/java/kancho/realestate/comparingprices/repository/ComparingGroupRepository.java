package kancho.realestate.comparingprices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.User;

public interface ComparingGroupRepository extends JpaRepository<ComparingGroup, Long> {

	List<ComparingGroup> findComparingGroupByUser(User user);
}
