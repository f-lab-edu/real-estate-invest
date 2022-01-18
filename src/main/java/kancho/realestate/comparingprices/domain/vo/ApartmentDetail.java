package kancho.realestate.comparingprices.domain.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;
import kancho.realestate.comparingprices.domain.vo.City;
import kancho.realestate.comparingprices.domain.vo.Gu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentDetail {
	// 아파트 정보
	@XmlElement(name = "지역코드")
	private String regionalCode;
	@XmlElement(name = "법정동")
	private String dong;
	@XmlElement(name = "지번")
	private String jibun;
	@XmlElement(name = "법정동본번코드")
	private String bonbun;
	@XmlElement(name = "법정동부번코드")
	private String bubun;
	@XmlElement(name = "아파트")
	private String apartmentName;
	@XmlElement(name = "건축년도")
	private int buildYear;
	@XmlElement(name = "도로명")
	private String roadAddress;

	// 아파트 가격 정보
	private long apartmentId;
	@XmlElement(name = "전용면적")
	private float areaForExclusiveUse;
	@XmlElement(name = "년")
	private int dealYear;
	@XmlElement(name = "월")
	private int dealMonth;
	@XmlElement(name = "일")
	private int dealDay;
	@XmlElement(name = "거래금액")
	private String dealAmount;
	@XmlElement(name = "층")
	private int floor;

	public ApartmentPrice getApartmentPrice() {
		return new ApartmentPrice(this);
	}

	public Apartment getApartment(){
		return new Apartment(getRegionalCode(), getCityName(), getGuName(), getDong(), getJibun(), getBonbun(), getBubun(), getApartmentName(), getBuildYear(), getRoadAddress());
	}

	private String getCityName(){
		return City.getCityName(getRegionalCode());
	}

	private String getGuName(){
		return Gu.getGuName(getRegionalCode());
	}
}
