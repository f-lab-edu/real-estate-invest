package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kancho.realestate.comparingprices.domain.dto.request.RequestSoaringPriceDto;
import lombok.Getter;

@Getter
@Table(indexes = @Index(name = "idx_created_date", columnList = "createdDate"))
@Entity
public class SoaringPrice extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Apartment apartment;

	private String areaForExclusiveUse;

	private LocalDate pastDate;

	private long pastPrice;

	private LocalDate latestDate;

	private long latestPrice;

	private String priceDifferenceUnit; // PERCENT, WON

	private long priceDifference;

	public SoaringPrice(Apartment apartment, String areaForExclusiveUse, LocalDate pastDate, long pastPrice, LocalDate latestDate,
		long latestPrice, String priceDifferenceUnit, long priceDifference) {
		this.apartment = apartment;
		this.areaForExclusiveUse = areaForExclusiveUse;
		this.pastDate = pastDate;
		this.pastPrice = pastPrice;
		this.latestDate = latestDate;
		this.latestPrice = latestPrice;
		this.priceDifferenceUnit = priceDifferenceUnit;
		this.priceDifference = priceDifference;
	}

	public enum Unit{
		PERCENT { // 퍼센트(%)
			public long calculatePriceDifference(long latestPrice, long pastPrice){
				return latestPrice * 100 / pastPrice;
			}
		},
		WON{ // 원(₩)
			public long calculatePriceDifference(long latestPrice, long pastPrice){
				return latestPrice - pastPrice;
			}
		};

		public abstract long calculatePriceDifference(long latestPrice, long pastPrice);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SoaringPrice))
			return false;
		SoaringPrice that = (SoaringPrice)o;
		return getId() == that.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
