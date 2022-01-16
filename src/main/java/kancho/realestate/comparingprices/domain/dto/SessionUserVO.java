package kancho.realestate.comparingprices.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SessionUserVO implements Serializable {
	private Long userId;
	private String account;

	private SessionUserVO() {
	}

	public SessionUserVO(Long userId, String account) {
		this.userId = userId;
		this.account = account;
	}
}
