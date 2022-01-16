package kancho.realestate.utils.api.storeaprtment.mapper;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import kancho.realestate.comparingprices.domain.vo.ApartmentDetail;
import kancho.realestate.comparingprices.domain.vo.ApartmentDetails;
import kancho.realestate.comparingprices.exception.InvalidApartmentXmlException;

public class ApartmentXmlMapper {

	public List<ApartmentDetail> apartmentXmlToList(String xmlString) throws JAXBException {
		String targetXml = getTargetXml(xmlString);
		ApartmentDetails apartmentDetails = (ApartmentDetails)getUnmarshaller()
			.unmarshal(new StringReader(targetXml));

		return apartmentDetails.getApartmentDetailList();
	}

	private String getTargetXml(String xmlString) {
		String start = "<items>";
		String end = "</items>";
		if (!isCorrectXml(xmlString, start, end)) {
			throw new InvalidApartmentXmlException(xmlString);
		}

		return xmlString.substring(xmlString.indexOf(start), xmlString.indexOf(end) + end.length());
	}

	private boolean isCorrectXml(String xmlString, String start, String end) {
		if (xmlString.contains(start) && xmlString.contains(end)) {
			return true;
		}

		return false;
	}

	private Unmarshaller getUnmarshaller() throws JAXBException {
		return getJAXBContext().createUnmarshaller();
	}

	private JAXBContext getJAXBContext() throws JAXBException {
		return JAXBContext.newInstance(ApartmentDetails.class);
	}
}
