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

	public RequestApartmentDto() {
	}

	private RequestApartmentDto(String regionalCode, String city, String gu, String dong, String jibun,
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

	public static Apartment toApartment(RequestApartmentDto requestApartmentDto) {
		return new Apartment(requestApartmentDto.getRegionalCode(), requestApartmentDto.getCity(), requestApartmentDto.getGu(),
			requestApartmentDto.getDong(), requestApartmentDto.getJibun(), requestApartmentDto.getBonbun(), requestApartmentDto.getBubun(),
			requestApartmentDto.getApartmentName(), requestApartmentDto.getBuildYear(), requestApartmentDto.getRoadAddress());
	}
}
