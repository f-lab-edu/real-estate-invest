package kancho.realestate.comparingprices.exception;

public class InvalidRegionalCodeException extends RuntimeException{
	public InvalidRegionalCodeException(String regionalCode) {
		super(regionalCode + "는 존재하지 않는 지역번호 입니다.");
	}
}
