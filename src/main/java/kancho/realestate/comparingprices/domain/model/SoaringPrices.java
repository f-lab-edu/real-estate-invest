package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SoaringPrices {
	private int id;
	private int apartmentId;
	private LocalDate pastDate;
	private long pastPrice;
	private LocalDate latestDate;
	private long latestPrice;
	private String priceDifferenceUnit; // PERCENT, WON
	private long priceDifference;
	private LocalDateTime createDdtm;

	public enum Unit{
		PERCENT,  // 퍼센트(%)
		WON       // 원(₩)
	}

	@Override
	public String toString() {
		return "SoaringPrices{" +
			"id=" + id +
			", apartmentId=" + apartmentId +
			", pastDate=" + pastDate +
			", pastPrice=" + pastPrice +
			", localDate=" + latestDate +
			", latestPrice=" + latestPrice +
			", priceDifferenceUnit='" + priceDifferenceUnit + '\'' +
			", priceDifference='" + priceDifference + '\'' +
			", createDdtm=" + createDdtm +
			'}';
	}
}
