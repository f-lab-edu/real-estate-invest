package kancho.realestate.comparingprices.domain.dto.response;

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
}
