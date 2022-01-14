package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;

@Getter
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

	// mybatis로 insert 후 입력받으려면 필요
	protected void setUserNo(Long userNo) {
		this.userNo = userNo;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if(!(o instanceof User))
			return false;
		User user = (User)o;
		return Objects.equals(getUserNo(), user.getUserNo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserNo());
	}
}
