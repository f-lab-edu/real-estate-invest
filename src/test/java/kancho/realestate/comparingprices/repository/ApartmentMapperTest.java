package kancho.realestate.comparingprices.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.model.Apartment;

@SpringBootTest
@Transactional
class ApartmentMapperTest {

	@Autowired
	private ApartmentMapper apartmentMapper;

	@Test
	public void insertApartment() {
		Apartment apartment = new Apartment("34302", "서울특별시", "강남구", "개포동", "22",
			"43", "0002", "테스트 아파트",1988, "언주로 103");
		apartmentMapper.save(apartment);
		Apartment findApartment = apartmentMapper.findByRegionalCodeAndDongAndJibunAndApartmentName(apartment).orElseThrow(IllegalStateException::new);
		assertThat(findApartment.getApartmentName()).isEqualTo(apartment.getApartmentName());
	}
}