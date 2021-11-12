package kancho.realestate.comparingprices.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATE_USER_ACCOUNT("ERROR_0001"),
	ILLEGAL_USER_NAME("ERROR_0002"),
	ILLEGAL_PASSWORD("ERROR_0003");

	private String code;

	ErrorCode(String code) {
		this.code = code;
	}
}