package kancho.realestate.utils.api.storeaprtment;

import java.util.List;
import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import kancho.realestate.comparingprices.domain.model.ApartmentDetail;
import kancho.realestate.comparingprices.exception.InvalidApartmentXmlException;

class ApartmentXmlMapperTest {
	private final ApartmentXmlMapper apartmentXmlMapper = new ApartmentXmlMapper();

	@Test
	void items_태그가_포함된_xml데이터를_입력하면_파싱_성공() throws JAXBException {
		String twoApartmnetXmlString = "<items><item><아파트>땡땡아파트</아파트></item><item><아파트>모모아파트</아파트></item></items>";

		List<ApartmentDetail> apartmentDetailList = apartmentXmlMapper.apartmentXmlToList(twoApartmnetXmlString);

		assertThat(apartmentDetailList.size())
			.isEqualTo(2);
	}

	@Test
	void items_태그가_미포함된_xml데이터를_입력하면_예외_발생() throws JAXBException {
		String incorrectXmlString = "<incorrectTag></incorrectTag>";

		assertThatThrownBy(() -> {
			List<ApartmentDetail> apartmentDetailList = apartmentXmlMapper.apartmentXmlToList(incorrectXmlString);
		}).isInstanceOf(InvalidApartmentXmlException.class);
	}
}