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
	public String toString() {
		return "User{" +
			"userNo=" + userNo +
			", id='" + id + '\'' +
			", password='" + password + '\'' +
			", joinDttm=" + joinDttm +
			", lastLoginDttm=" + lastLoginDttm +
			", deleteDttm=" + deleteDttm +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return Objects.equals(getUserNo(), user.getUserNo()) && Objects.equals(getId(), user.getId())
			&& Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getJoinDttm(),
			user.getJoinDttm()) && Objects.equals(getLastLoginDttm(), user.getLastLoginDttm())
			&& Objects.equals(getDeleteDttm(), user.getDeleteDttm());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserNo(), getId(), getPassword(), getJoinDttm(), getLastLoginDttm(), getDeleteDttm());
	}
}
