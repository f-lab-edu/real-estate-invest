package kancho.realestate.comparingprices.domain.dto.request;

import kancho.realestate.comparingprices.domain.model.Apartment;
import lombok.Getter;

@Getter
public class RequestApartmentDto {

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

	private RequestApartmentDto() {
	}

	public RequestApartmentDto(String regionalCode) {
		this.regionalCode = regionalCode;
	}

	public RequestApartmentDto(String regionalCode, String city, String gu, String dong, String jibun,
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

	public Apartment toApartment() {
		return new Apartment(getRegionalCode(), getCity(), getGu(),
			getDong(), getJibun(), getBonbun(), getBubun(),
			getApartmentName(), getBuildYear(), getRoadAddress());
	}
}
