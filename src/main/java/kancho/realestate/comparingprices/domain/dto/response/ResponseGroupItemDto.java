package kancho.realestate.comparingprices.domain.dto.response;

import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.GroupItem;
import lombok.Getter;

@Getter
public class ResponseGroupItemDto {

	private Long id;
	private Long groupId;
	private Long apartmentId;

	public ResponseGroupItemDto() {
	}

	public ResponseGroupItemDto(Long id, Long groupId, Long apartmentId) {
		this.id = id;
		this.groupId = groupId;
		this.apartmentId = apartmentId;
	}

	public static ResponseGroupItemDto from(GroupItem groupItem) {
		return new ResponseGroupItemDto(groupItem.getId(), groupItem.getGroupId(),
			groupItem.getApartmentId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponseGroupItemDto that = (ResponseGroupItemDto)o;
		return Objects.equals(getId(), that.getId()) && Objects.equals(getGroupId(), that.getGroupId())
			&& Objects.equals(getApartmentId(), that.getApartmentId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getGroupId(), getApartmentId());
	}
}
