package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// @Builder
@Getter
@ToString
@EqualsAndHashCode
public class User {
	private Long userNo;
	private String id;
	private String password;
	private LocalDateTime joinDttm;
	private LocalDateTime lastLoginDttm;
	private LocalDateTime deleteDttm;

	public User(String id, String password) {
		validateId(id);
		this.id = id;
		this.password = password;
		this.joinDttm = LocalDateTime.now();
		this.lastLoginDttm = LocalDateTime.now();
	}

	private void validateId(String id) {

	}

}
