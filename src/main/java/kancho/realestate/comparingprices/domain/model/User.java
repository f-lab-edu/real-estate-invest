package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
	private Long userNo;
	private String id;
	private String password;
	private LocalDateTime joinDttm;
	private LocalDateTime lastLoginDttm;
	private LocalDateTime deleteDttm;

	public static User createUser(String id, String password) {
		return new User(id, password);
	}

	private User(String id, String password) {
		validateId(id);
		validatePassword(password);
		this.id = id;
		this.password = password;
		this.joinDttm = LocalDateTime.now();
		this.lastLoginDttm = LocalDateTime.now();
	}

	private void validateId(String id) {

	}

	private void validatePassword(String password) {

	}

}
