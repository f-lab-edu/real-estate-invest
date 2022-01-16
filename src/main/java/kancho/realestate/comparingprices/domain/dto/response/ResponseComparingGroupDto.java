package kancho.realestate.comparingprices.domain.dto.response;

import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import lombok.Getter;

@Getter
public class ResponseComparingGroupDto {

	private Long id;
	private Long userId;
	private String name;

	private ResponseComparingGroupDto() {
	}

	private ResponseComparingGroupDto(Long id, Long userId, String name) {
		this.id = id;
		this.userId = userId;
		this.name = name;
	}

	public static ResponseComparingGroupDto from(ComparingGroup comparingGroup) {
		return new ResponseComparingGroupDto(comparingGroup.getId(), comparingGroup.getId(),
			comparingGroup.getGroupName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponseComparingGroupDto groupDto = (ResponseComparingGroupDto)o;
		return Objects.equals(getId(), groupDto.getId()) && Objects.equals(this.getUserId(),
			groupDto.getUserId()) && Objects.equals(getName(), groupDto.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), this.getUserId(), getName());
	}

	@Override
	public String toString() {
		return "ResponseComparingGroupDto{" +
			"id=" + id +
			", userId=" + userId +
			", name='" + name + '\'' +
			'}';
	}
}
