package kancho.realestate.comparingprices.domain.dto.response;

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
}
