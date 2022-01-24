package kancho.realestate.comparingprices.domain.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class SessionUserVO extends User {

	private Long userId;

	public SessionUserVO(String username, String password,
		Collection<? extends GrantedAuthority> authorities, Long userId) {
		super(username, password, authorities);
		this.userId = userId;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}
}
