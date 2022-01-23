package kancho.realestate.comparingprices.domain.dto.request;

import java.time.LocalDateTime;

import kancho.realestate.comparingprices.domain.vo.Gu;
import kancho.realestate.comparingprices.exception.InvalidDealYearAndMonthException;
import kancho.realestate.comparingprices.exception.InvalidRegionalCodeException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestApartmentDetailDto {
	private String regionalCode;
	private int dealYear;
	private int dealMonth;

	public RequestApartmentDetailDto(String regionalCode, int dealYear, int dealMonth) {
		if (!isCorrectRegionalCode(regionalCode)){
			throw new InvalidRegionalCodeException(regionalCode);
		}

		if (!isCorrectDealYearAndMonth(dealYear, dealMonth)) {
			throw new InvalidDealYearAndMonthException(dealYear, dealMonth);
		}

		this.regionalCode = regionalCode;
		this.dealYear = dealYear;
		this.dealMonth = dealMonth;
	}

	private boolean isCorrectRegionalCode(String regionalCode) {
		return Gu.isCorrectRegionalCode(regionalCode);
	}

	private boolean isCorrectDealYearAndMonth(int dealYear, int dealMonth) {
		int nowYear = LocalDateTime.now().getYear();
		int nowMonth = LocalDateTime.now().getMonthValue();

		if (dealYear < nowYear) {
			if (dealMonth >= 1 && dealMonth <= 12) {
				return true;
			}
		} else if (dealYear == nowYear) {
			if (dealMonth >= 1 && dealMonth <= nowMonth) {
				return true;
			}
		}
		return false;
	}
}
