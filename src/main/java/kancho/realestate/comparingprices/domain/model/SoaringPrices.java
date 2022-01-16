package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Table(indexes = @Index(name = "idx_created_date", columnList = "createdDate"))
@Entity
public class SoaringPrices extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Apartment apartment;

	private LocalDate pastDate;

	private long pastPrice;

	private LocalDate latestDate;

	private long latestPrice;

	private String priceDifferenceUnit; // PERCENT, WON

	private long priceDifference;

	protected SoaringPrices() {
	}

	public enum Unit {
		PERCENT,  // 퍼센트(%)
		WON       // 원(₩)
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SoaringPrices))
			return false;
		SoaringPrices that = (SoaringPrices)o;
		return getId() == that.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
