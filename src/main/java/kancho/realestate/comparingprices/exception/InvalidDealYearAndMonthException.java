package kancho.realestate.comparingprices.exception;

public class InvalidDealYearAndMonthException extends RuntimeException{
	public InvalidDealYearAndMonthException(int dealYear, int dealMonth) {
		super(dealYear + "년 " + dealMonth + "월은 올바른 날짜가 아닙니다.");
	}
}
