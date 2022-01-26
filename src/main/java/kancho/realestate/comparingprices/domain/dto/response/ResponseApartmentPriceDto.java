package kancho.realestate.comparingprices.domain.dto.response;

import java.math.BigDecimal;
import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseApartmentPriceDto {
	private long apartmentId;
	private BigDecimal areaForExclusiveUse;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private int dealAmount;
	private int floor;

	public ResponseApartmentPriceDto(long apartmentId, BigDecimal areaForExclusiveUse, int dealYear, int dealMonth,
		int dealDay, int dealAmount, int floor) {
		this.apartmentId = apartmentId;
		this.areaForExclusiveUse = areaForExclusiveUse;
		this.dealYear = dealYear;
		this.dealMonth = dealMonth;
		this.dealDay = dealDay;
		this.dealAmount = dealAmount;
		this.floor = floor;
	}

	public static ResponseApartmentPriceDto from(ApartmentPrice apartmentPrice) {
		return new ResponseApartmentPriceDto(
			apartmentPrice.getApartmentId(),
			apartmentPrice.getAreaForExclusiveUse(),
			apartmentPrice.getDealYear(),
			apartmentPrice.getDealMonth(),
			apartmentPrice.getDealDay(),
			apartmentPrice.getDealAmount(),
			apartmentPrice.getFloor());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponseApartmentPriceDto that = (ResponseApartmentPriceDto)o;
		return apartmentId == that.apartmentId && dealYear == that.dealYear && dealMonth == that.dealMonth
			&& dealDay == that.dealDay && dealAmount == that.dealAmount && floor == that.floor
			&& areaForExclusiveUse.compareTo(that.areaForExclusiveUse) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apartmentId, areaForExclusiveUse, dealYear, dealMonth, dealDay, dealAmount, floor);
	}
}

