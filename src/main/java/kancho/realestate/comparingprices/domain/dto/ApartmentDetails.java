package kancho.realestate.comparingprices.domain.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentDetails {
	@XmlElement(name = "item")
	private List<ApartmentDetail> apartmentDetailList;
}
