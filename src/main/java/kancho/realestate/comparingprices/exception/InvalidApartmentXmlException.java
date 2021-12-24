package kancho.realestate.comparingprices.exception;

import org.springframework.data.relational.core.sql.In;

public class InvalidApartmentXmlException extends RuntimeException{
	public InvalidApartmentXmlException (String xmlString) {
		super(xmlString + "은 올바르지 않은 xml 데이터입니다.");
	}
}
