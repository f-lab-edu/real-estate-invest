package kancho.realestate.comparingprices.domain.dto.response;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import lombok.Getter;

@Getter
public class ResponseGroupItemDto {

	private Long id;
	private Long groupId;
	private Long apartmentId;

	public ResponseGroupItemDto(Long id, Long groupId, Long apartmentId) {
		this.id = id;
		this.groupId = groupId;
		this.apartmentId = apartmentId;
	}

	public ResponseGroupItemDto from(GroupItem groupItem) {
		return new ResponseGroupItemDto(groupItem.getId(), groupItem.getGroupId(),
			groupItem.getApartmentId());
	}
}
