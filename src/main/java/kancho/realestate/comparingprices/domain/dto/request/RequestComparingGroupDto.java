package kancho.realestate.comparingprices.domain.dto.request;

import java.util.ArrayList;
import java.util.List;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.User;
import lombok.Getter;

@Getter
public class RequestComparingGroupDto {

	private Long userId;
	private String name;

	private RequestComparingGroupDto() {
	}

	public RequestComparingGroupDto(Long userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	public ComparingGroup toComparingGroup(User user) {
		return new ComparingGroup(user, getName());
	}
}
