package kancho.realestate.comparingprices.domain.dto.request;

import lombok.Data;

@Data
public class UserDto {
	private String id;
	private String password;

	/* @RequestBody에 사용하려면 Default constructor를 만들어야함 */
	public UserDto() {
	}

	public UserDto(String id, String password) {
		this.id = id;
		this.password = password;
	}
}
