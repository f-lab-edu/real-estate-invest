package kancho.realestate.utils.api.storeaprtment.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ApartmentPrice;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ApartmentServiceTest {

	@Autowired
	ApartmentService apartmentService;

	@Test
	void 모든_아파트_정보를_조회하면_값이_존재함() {
		List<Apartment> apartmentList = apartmentService.findAllApartments();
		assertThat(apartmentList).isNotNull();
	}

	@Test
	void 모든_아파트_가격정보를_조회하면_값이_존재함() {
		List<ApartmentPrice> apartmentPriceList = apartmentService.findAllApartmentsPrice();
		assertThat(apartmentPriceList).isNotNull();
	}

	@Test
	void 아파트_정보를_저장하면_반환되는_id는_양의_정수() {
		Apartment apartment = new Apartment("11680", "어떤시", "어떤구", "어떤동", "777", "7777", "7777", "어떤아파트", 2022,
			"어떤도로명");
		long id = apartmentService.save(apartment);
		assertThat(id).isGreaterThan(0);
	}
}