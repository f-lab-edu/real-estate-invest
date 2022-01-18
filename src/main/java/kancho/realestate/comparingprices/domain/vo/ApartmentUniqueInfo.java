package kancho.realestate.comparingprices.domain.vo;

import java.util.Objects;

import kancho.realestate.comparingprices.domain.model.Apartment;

public class ApartmentUniqueInfo {
	private String RegionalCode;
	private String Dong;
	private String Jibun;
	private String ApartmentName;

	public ApartmentUniqueInfo(Apartment apartment) {
		RegionalCode = apartment.getRegionalCode();
		Dong = apartment.getDong();
		Jibun = apartment.getJibun();
		ApartmentName = apartment.getApartmentName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ApartmentUniqueInfo that = (ApartmentUniqueInfo)o;
		return RegionalCode.equals(that.RegionalCode) && Dong.equals(that.Dong) && Jibun.equals(that.Jibun)
			&& ApartmentName
			.equals(that.ApartmentName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(RegionalCode, Dong, Jibun, ApartmentName);
	}
}
