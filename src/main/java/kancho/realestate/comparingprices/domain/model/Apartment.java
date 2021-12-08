package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Apartment apartment = (Apartment)o;
		return getId() == apartment.getId() && getBuildYear() == apartment.getBuildYear() && Objects.equals(
			getRegionalCode(), apartment.getRegionalCode()) && Objects.equals(getCity(), apartment.getCity())
			&& Objects.equals(getGu(), apartment.getGu()) && Objects.equals(getDong(),
			apartment.getDong()) && Objects.equals(getJibun(), apartment.getJibun()) && Objects.equals(
			getBonbun(), apartment.getBonbun()) && Objects.equals(getBubun(), apartment.getBubun())
			&& Objects.equals(getApartmentName(), apartment.getApartmentName()) && Objects.equals(
			getRoadAddress(), apartment.getRoadAddress()) && Objects.equals(getDeleteDttm(),
			apartment.getDeleteDttm());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getRegionalCode(), getCity(), getGu(), getDong(), getJibun(), getBonbun(),
			getBubun(),
			getApartmentName(), getBuildYear(), getRoadAddress(), getDeleteDttm());
	}

	@Override
	public String toString() {
		return "Apartment{" +
			"id=" + id +
			", regionalCode='" + regionalCode + '\'' +
			", city='" + city + '\'' +
			", gu='" + gu + '\'' +
			", dong='" + dong + '\'' +
			", jibun='" + jibun + '\'' +
			", bonbun='" + bonbun + '\'' +
			", bubun='" + bubun + '\'' +
			", apartmentName='" + apartmentName + '\'' +
			", buildYear=" + buildYear +
			", roadAddress='" + roadAddress + '\'' +
			", deleteDttm=" + deleteDttm +
			'}';
	}
}
