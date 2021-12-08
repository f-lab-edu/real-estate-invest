package kancho.realestate.comparingprices.domain.dto.request;

import java.time.LocalDateTime;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import lombok.Getter;

@Getter
public class RequestComparingGroupDto {

	private Long userNo;
	private String name;

	public RequestComparingGroupDto() {
	}

	public RequestComparingGroupDto(Long userNo, String name) {
		this.userNo = userNo;
		this.name = name;
	}

	public ComparingGroup toComparingGroup() {
		return new ComparingGroup(getUserNo(), getName());
	}
}
