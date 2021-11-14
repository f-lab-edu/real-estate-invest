package kancho.realestate.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
		Apartment apartment = new Apartment("11680", "서울특별시", "강남구", "개포동", "655-2",
			"0655", "0002", "개포2차현대아파트(220)",1988, "언주로 103");
		apartmentMapper.save(apartment);
		List<Apartment> apartments = apartmentMapper.findAll();
		assertThat(apartments.size()).isEqualTo(1);
	}
}