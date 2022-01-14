package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Apartment {
	private long id;
	private String regionalCode;
	private String city;
	private String gu;
	private String dong;
	private String jibun;
	private String bonbun;
	private String bubun;
	private String apartmentName;
	private int buildYear;
	private String roadAddress;
	private LocalDateTime deleteDttm;

	private Apartment() {}

	public Apartment(String regionalCode, String city, String gu, String dong, String jibun,
		String bonbun, String bubun, String apartmentName, int buildYear, String roadAddress) {
		this.regionalCode = regionalCode;
		this.city = city;
		this.gu = gu;
		this.dong = dong;
		this.jibun = jibun;
		this.bonbun = bonbun;
		this.bubun = bubun;
		this.apartmentName = apartmentName;
		this.buildYear = buildYear;
		this.roadAddress = roadAddress;
	}

	public Apartment(long id, String regionalCode, String city, String gu, String dong, String jibun,
		String bonbun, String bubun, String apartmentName, int buildYear, String roadAddress,
		LocalDateTime deleteDttm) {
		this.id = id;
		this.regionalCode = regionalCode;
		this.city = city;
		this.gu = gu;
		this.dong = dong;
		this.jibun = jibun;
		this.bonbun = bonbun;
		this.bubun = bubun;
		this.apartmentName = apartmentName;
		this.buildYear = buildYear;
		this.roadAddress = roadAddress;
		this.deleteDttm = deleteDttm;
	}

	public Apartment(String regionalCode, String dong, String jibun, String apartmentName) {
		this.regionalCode = regionalCode;
		this.dong = dong;
		this.jibun = jibun;
		this.apartmentName = apartmentName;
	}

	public ApartmentUniqueInfo getApartmentUniqueInfo() {
		return new ApartmentUniqueInfo(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		// if (o == null || getClass() != o.getClass())
		if(!(o instanceof Apartment))
			return false;
		Apartment apartment = (Apartment)o;
		return getId() == apartment.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
