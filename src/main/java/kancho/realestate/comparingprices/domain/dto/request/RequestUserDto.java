package kancho.realestate.comparingprices.domain.dto.request;

import lombok.Getter;

@Getter
public class RequestUserDto {
	private String account;
	private String password;

	/* @RequestBody에 사용하려면 Default constructor를 만들어야함 */
	private RequestUserDto() {
	}

	public RequestUserDto(String account, String password) {
		this.account = account;
		this.password = password;
	}

	@Override
	public String toString() {
		return "RequestUserDto{" +
			"account='" + account + '\'' +
			", password='" + password + '\'' +
			'}';
	}
}
