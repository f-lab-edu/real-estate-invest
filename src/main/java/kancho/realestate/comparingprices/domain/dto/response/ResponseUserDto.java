package kancho.realestate.comparingprices.domain.dto.response;

import java.time.LocalDateTime;

import kancho.realestate.comparingprices.domain.model.User;
import lombok.Getter;

@Getter
public class ResponseUserDto {
	private Long userId;
	private String account;
	private LocalDateTime joinDttm;
	private LocalDateTime lastLoginDttm;

	private ResponseUserDto() {
	}

	public ResponseUserDto(Long userId, String account, LocalDateTime joinDttm, LocalDateTime lastLoginDttm) {
		this.userId = userId;
		this.account = account;
		this.joinDttm = joinDttm;
		this.lastLoginDttm = lastLoginDttm;
	}

	public static ResponseUserDto from(User user) {
		return new ResponseUserDto(user.getId(), user.getAccount(), user.getCreatedDate(), user.getLastLoginDttm());
	}
}
