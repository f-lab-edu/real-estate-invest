package kancho.realestate.comparingprices.domain.model;

import java.util.Objects;

public class ApartmentPriceUniqueInfo {
	private long apartmentId;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private int dealAmount;
	private int floor;

	public ApartmentPriceUniqueInfo(ApartmentPrice apartmentPrice) {
		this.apartmentId = apartmentPrice.getApartmentId();
		this.dealYear = apartmentPrice.getDealYear();
		this.dealMonth = apartmentPrice.getDealMonth();
		this.dealDay = apartmentPrice.getDealDay();
		this.dealAmount = apartmentPrice.getDealAmount();
		this.floor = apartmentPrice.getFloor();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ApartmentPriceUniqueInfo that = (ApartmentPriceUniqueInfo)o;
		return apartmentId == that.apartmentId && dealYear == that.dealYear && dealMonth == that.dealMonth
			&& dealDay == that.dealDay && dealAmount == that.dealAmount && floor == that.floor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apartmentId, dealYear, dealMonth, dealDay, dealAmount, floor);
	}
}
