package kancho.realestate.comparingprices.domain.dto.response;


import java.time.LocalDate;

import kancho.realestate.comparingprices.domain.model.SoaringPrice;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseSoaringPriceDto {
	private int id;
	private long apartmentId;
	private String areaForExclusiveUse;
	private LocalDate pastDate;
	private long pastPrice;
	private LocalDate latestDate;
	private long latestPrice;
	private String priceDifferenceUnit; // PERCENT, WON
	private long priceDifference;

	public ResponseSoaringPriceDto(int id, long apartmentId, String areaForExclusiveUse, LocalDate pastDate,
		long pastPrice, LocalDate latestDate, long latestPrice, String priceDifferenceUnit, long priceDifference) {
		this.id = id;
		this.apartmentId = apartmentId;
		this.areaForExclusiveUse = areaForExclusiveUse;
		this.pastDate = pastDate;
		this.pastPrice = pastPrice;
		this.latestDate = latestDate;
		this.latestPrice = latestPrice;
		this.priceDifferenceUnit = priceDifferenceUnit;
		this.priceDifference = priceDifference;
	}

	public static ResponseSoaringPriceDto from(SoaringPrice soaringPrice) {
		return new ResponseSoaringPriceDto(
			soaringPrice.getId(),
			soaringPrice.getApartment().getId(),
			soaringPrice.getAreaForExclusiveUse().toString(),
			soaringPrice.getPastDate(),
			soaringPrice.getPastPrice(),
			soaringPrice.getLatestDate(),
			soaringPrice.getLatestPrice(),
			soaringPrice.getPriceDifferenceUnit().toString(),
			soaringPrice.getPriceDifference());
	}
}
