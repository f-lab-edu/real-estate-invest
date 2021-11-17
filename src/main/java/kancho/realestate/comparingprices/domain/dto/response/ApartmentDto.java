package kancho.realestate.comparingprices.domain.dto.response;

import kancho.realestate.comparingprices.domain.model.Apartment;

public class ApartmentDto {

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

	public ApartmentDto() {
	}

	public ApartmentDto(long id, String regionalCode, String city, String gu, String dong, String jibun,
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

	public static ApartmentDto of(Apartment apartment) {
		return new ApartmentDto(apartment.getId(), apartment.getRegionalCode(), apartment.getCity(), apartment.getGu(),
			apartment.getDong(), apartment.getJibun(), apartment.getBonbun(), apartment.getBubun(),
			apartment.getApartmentName(), apartment.getBuildYear(), apartment.getRoadAddress());
	}

	public static Apartment toApartment(ApartmentDto apartmentDto) {
		return new Apartment(apartmentDto.getId(),apartmentDto.getRegionalCode(), apartmentDto.getCity(), apartmentDto.getGu(),
			apartmentDto.getDong(), apartmentDto.getJibun(), apartmentDto.getBonbun(), apartmentDto.getBubun(),
			apartmentDto.getApartmentName(), apartmentDto.getBuildYear(), apartmentDto.getRoadAddress());
	}

	public long getId() {
		return id;
	}

	public String getRegionalCode() {
		return regionalCode;
	}

	public String getCity() {
		return city;
	}

	public String getGu() {
		return gu;
	}

	public String getDong() {
		return dong;
	}

	public String getJibun() {
		return jibun;
	}

	public String getBonbun() {
		return bonbun;
	}

	public String getBubun() {
		return bubun;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public String getRoadAddress() {
		return roadAddress;
	}
}
