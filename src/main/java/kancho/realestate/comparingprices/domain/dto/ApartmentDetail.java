package kancho.realestate.comparingprices.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
	// private String city;
	// private String gu;
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
}
