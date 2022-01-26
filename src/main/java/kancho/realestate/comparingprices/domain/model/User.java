package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String account;

	@Column(nullable = false)
	private String password;

	private LocalDateTime lastLoginDttm;

	private LocalDateTime deleteDttm;

	protected User() {
	}

	public User(String account, String password) {
		validateId(account);
		this.account = account;
		this.password = password;
	}

	private void validateId(String id) {

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User user = (User)o;
		return Objects.equals(this.getId(), user.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}

	// 메서드의 유연성과 테스트 용이성을 위해 LocalDateTime을 인자로 받게 함
	public void updateLastLoginDttm(LocalDateTime loginTime) {
		this.lastLoginDttm = loginTime == null ? LocalDateTime.now() : loginTime;
	}
}
