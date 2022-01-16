package kancho.realestate.comparingprices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;

public interface GroupItemRepository extends JpaRepository<GroupItem, Long> {

	List<GroupItem> findGroupItemByComparingGroup(ComparingGroup comparingGroup);
}
