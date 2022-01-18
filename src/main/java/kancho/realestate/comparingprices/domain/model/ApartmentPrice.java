package kancho.realestate.comparingprices.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kancho.realestate.comparingprices.domain.vo.ApartmentDetail;
import kancho.realestate.comparingprices.domain.vo.ApartmentPriceUniqueInfo;
import lombok.Getter;

@Getter
@Entity
public class ApartmentPrice extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long apartmentId;

	private float areaForExclusiveUse;

	private int dealYear;

	private int dealMonth;

	private int dealDay;

	private int dealAmount;

	private int floor;

	protected ApartmentPrice() {}

	public ApartmentPrice(ApartmentDetail detail) {
		this.apartmentId = detail.getApartmentId();
		this.areaForExclusiveUse = detail.getAreaForExclusiveUse();
		this.dealYear = detail.getDealYear();
		this.dealMonth = detail.getDealMonth();
		this.dealDay = detail.getDealDay();
		this.dealAmount = parseDealAmount(detail.getDealAmount());
		this.floor = detail.getFloor();
	}

	private int parseDealAmount(String dealAmount) {
			dealAmount = dealAmount.trim().replace(",",""); // 공백 제거
			return Integer.parseInt(dealAmount);
	}

	public ApartmentPriceUniqueInfo getApartmentPriceUniqueInfo() {
		return new ApartmentPriceUniqueInfo(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ApartmentPrice))
			return false;

		ApartmentPrice that = (ApartmentPrice)o;
		BigDecimal thisAreaForExclusiveUse = new BigDecimal(that.areaForExclusiveUse);
		BigDecimal thatAreaForExclusiveUse = new BigDecimal(areaForExclusiveUse);
		
		return apartmentId == that.apartmentId
			&& thisAreaForExclusiveUse.compareTo(thatAreaForExclusiveUse) == 0 && dealYear == that.dealYear
			&& dealMonth == that.dealMonth && dealDay == that.dealDay && dealAmount == that.dealAmount
			&& floor == that.floor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apartmentId, areaForExclusiveUse, dealYear, dealMonth, dealDay, dealAmount, floor);
	}
}
