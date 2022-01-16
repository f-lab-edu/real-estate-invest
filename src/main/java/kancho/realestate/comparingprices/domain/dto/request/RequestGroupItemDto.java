package kancho.realestate.comparingprices.domain.dto.request;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import lombok.Getter;

@Getter
public class RequestGroupItemDto {

	private Long groupId;
	private Long apartmentId;

	private RequestGroupItemDto() {
	}

	public RequestGroupItemDto(Long groupId, Long apartmentId) {
		this.groupId = groupId;
		this.apartmentId = apartmentId;
	}

	public GroupItem toGroupItem(ComparingGroup group, Apartment apartment) {
		return new GroupItem(group,apartment);
	}
}
