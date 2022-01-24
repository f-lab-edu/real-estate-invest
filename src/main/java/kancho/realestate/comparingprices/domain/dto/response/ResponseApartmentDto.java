package kancho.realestate.comparingprices.domain.dto.response;

import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.Apartment;
import lombok.Getter;

@Getter
public class ResponseApartmentDto {

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

	private ResponseApartmentDto() {
	}

	public ResponseApartmentDto(long id, String regionalCode, String city, String gu, String dong, String jibun,
		String bonbun, String bubun, String apartmentName, int buildYear, String roadAddress) {
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
	}

	public static ResponseApartmentDto from(Apartment apartment) {
		return new ResponseApartmentDto(apartment.getId(), apartment.getRegionalCode(), apartment.getCity(), apartment.getGu(),
			apartment.getDong(), apartment.getJibun(), apartment.getBonbun(), apartment.getBubun(),
			apartment.getApartmentName(), apartment.getBuildYear(), apartment.getRoadAddress());
	}

	@Override
	public String toString() {
		return "ResponseApartmentDto{" +
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
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponseApartmentDto that = (ResponseApartmentDto)o;
		return getId() == that.getId() && getBuildYear() == that.getBuildYear() && Objects.equals(
			getRegionalCode(), that.getRegionalCode()) && Objects.equals(getCity(), that.getCity())
			&& Objects.equals(getGu(), that.getGu()) && Objects.equals(getDong(), that.getDong())
			&& Objects.equals(getJibun(), that.getJibun()) && Objects.equals(getBonbun(),
			that.getBonbun()) && Objects.equals(getBubun(), that.getBubun()) && Objects.equals(
			getApartmentName(), that.getApartmentName()) && Objects.equals(getRoadAddress(),
			that.getRoadAddress());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getRegionalCode(), getCity(), getGu(), getDong(), getJibun(), getBonbun(),
			getBubun(),
			getApartmentName(), getBuildYear(), getRoadAddress());
	}
}
