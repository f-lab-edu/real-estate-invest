package kancho.realestate.comparingprices.domain.dto.response;

import java.time.LocalDateTime;

import kancho.realestate.comparingprices.domain.model.User;
import lombok.Getter;

@Getter
public class ResponseUserDto {
	private Long userNo;
	private String id;
	private LocalDateTime joinDttm;
	private LocalDateTime lastLoginDttm;

	public ResponseUserDto() {
	}

	public ResponseUserDto(Long userNo, String id, LocalDateTime joinDttm, LocalDateTime lastLoginDttm) {
		this.userNo = userNo;
		this.id = id;
		this.joinDttm = joinDttm;
		this.lastLoginDttm = lastLoginDttm;
	}

	public static ResponseUserDto from(User user) {
		return new ResponseUserDto(user.getUserNo(), user.getId(), user.getJoinDttm(), user.getLastLoginDttm());
	}
}
