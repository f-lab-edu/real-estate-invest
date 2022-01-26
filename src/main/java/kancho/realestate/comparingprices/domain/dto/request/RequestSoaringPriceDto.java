package kancho.realestate.comparingprices.domain.dto.request;

import java.time.LocalDate;

import kancho.realestate.comparingprices.domain.model.SoaringPrice;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestSoaringPriceDto {
	private long apartmentId;
	private String areaForExclusiveUse;
	private LocalDate pastDate;
	private long pastPrice;
	private LocalDate latestDate;
	private long latestPrice;
	private SoaringPrice.Unit priceDifferenceUnit; // PERCENT, WON
	private long priceDifference;

	public RequestSoaringPriceDto(long apartmentId, String areaForExclusiveUse, LocalDate pastDate, long pastPrice,
		LocalDate latestDate, long latestPrice, SoaringPrice.Unit priceDifferenceUnit, long priceDifference) {
		this.apartmentId = apartmentId;
		this.areaForExclusiveUse = areaForExclusiveUse;
		this.pastDate = pastDate;
		this.pastPrice = pastPrice;
		this.latestDate = latestDate;
		this.latestPrice = latestPrice;
		this.priceDifferenceUnit = priceDifferenceUnit;
		this.priceDifference = priceDifference;
	}
}
