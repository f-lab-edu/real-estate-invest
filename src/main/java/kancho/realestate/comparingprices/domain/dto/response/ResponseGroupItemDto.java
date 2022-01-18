package kancho.realestate.comparingprices.domain.dto.response;

import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import lombok.Getter;

@Getter
public class ResponseGroupItemDto {

	private Long id;
	private ResponseComparingGroupDto comparingGroupDto;
	private Long apartmentId;

	private ResponseGroupItemDto() {
	}

	public ResponseGroupItemDto(Long id, ResponseComparingGroupDto comparingGroupDto, Long apartmentId) {
		this.id = id;
		this.comparingGroupDto = comparingGroupDto;
		this.apartmentId = apartmentId;
	}

	public static ResponseGroupItemDto from(GroupItem groupItem) {
		return new ResponseGroupItemDto(groupItem.getId(), ResponseComparingGroupDto.from(groupItem.getComparingGroup()),
			groupItem.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponseGroupItemDto that = (ResponseGroupItemDto)o;
		return Objects.equals(getId(), that.getId()) && Objects.equals(getComparingGroupDto(),
			that.getComparingGroupDto()) && Objects.equals(getApartmentId(), that.getApartmentId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getComparingGroupDto(), getApartmentId());
	}
}
