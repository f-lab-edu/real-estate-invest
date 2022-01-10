package kancho.realestate.comparingprices.domain.model;

import java.util.Objects;

import lombok.Getter;

@Getter
public class ApartmentPrice {
	private long id;
	private long apartmentId;
	private float areaForExclusiveUse;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private int dealAmount;
	private int floor;

	private ApartmentPrice() {}

	ApartmentPrice(ApartmentDetail detail) {
		this.apartmentId = detail.getApartmentId();
		this.areaForExclusiveUse = detail.getAreaForExclusiveUse();
		this.dealYear = detail.getDealYear();
		this.dealMonth = detail.getDealMonth();
		this.dealDay = detail.getDealDay();
		this.dealAmount = parseDealAmount(detail.getDealAmount());
		this.floor = detail.getFloor();
	}

	private int parseDealAmount(String dealAmount) {
			dealAmount = dealAmount.trim(); // 공백 제거
			// 금액을 나누는 ',' 부호 제거
			String[] strArr = dealAmount.split(",");
			StringBuilder sb = new StringBuilder();
			for (String str: strArr) {
				sb.append(str);
			}

			return Integer.parseInt(sb.toString());
	}

	public ApartmentPriceUniqueInfo getApartmentPriceUniqueInfo() {
		return new ApartmentPriceUniqueInfo(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ApartmentPrice that = (ApartmentPrice)o;
		return apartmentId == that.apartmentId
			&& Float.compare(that.areaForExclusiveUse, areaForExclusiveUse) == 0 && dealYear == that.dealYear
			&& dealMonth == that.dealMonth && dealDay == that.dealDay && dealAmount == that.dealAmount
			&& floor == that.floor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apartmentId, areaForExclusiveUse, dealYear, dealMonth, dealDay, dealAmount, floor);
	}
}
