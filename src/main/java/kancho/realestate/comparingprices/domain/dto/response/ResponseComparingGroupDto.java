package kancho.realestate.comparingprices.domain.dto.response;

import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import lombok.Getter;

@Getter
public class ResponseComparingGroupDto {

	private Long id;
	private Long userNo;
	private String name;

	public ResponseComparingGroupDto() {
	}

	private ResponseComparingGroupDto(Long id, Long userNo, String name) {
		this.id = id;
		this.userNo = userNo;
		this.name = name;
	}

	public static ResponseComparingGroupDto from(ComparingGroup comparingGroup) {
		return new ResponseComparingGroupDto(comparingGroup.getId(), comparingGroup.getUserNo(),
			comparingGroup.getName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponseComparingGroupDto groupDto = (ResponseComparingGroupDto)o;
		return Objects.equals(getId(), groupDto.getId()) && Objects.equals(getUserNo(),
			groupDto.getUserNo()) && Objects.equals(getName(), groupDto.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUserNo(), getName());
	}
}
