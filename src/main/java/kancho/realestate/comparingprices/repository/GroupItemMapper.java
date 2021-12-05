package kancho.realestate.comparingprices.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.GroupItem;

@Mapper
public interface GroupItemMapper {

	void saveGroupItem(GroupItem item);

	List<GroupItem> findByGroupId(Long groupId);
}
