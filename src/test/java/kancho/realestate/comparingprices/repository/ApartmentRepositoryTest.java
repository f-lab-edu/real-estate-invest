package kancho.realestate.comparingprices.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;

import kancho.realestate.comparingprices.domain.model.Apartment;

class ApartmentRepositoryTest extends RepositoryTest{

	@Autowired
	private ApartmentRepository apartmentRepository;

	@Test
	public void insertApartment() {
		//given
		Apartment apartment = new Apartment("34302", "서울특별시", "강남구", "개포동", "22",
			"43", "0002", "테스트 아파트", 1988, "언주로 103");

		//when
		apartmentRepository.save(apartment);

		//then
		assertThat(apartment.getId()).isNotNull();
	}

	@DisplayName("페이징을 적용한 전체 조회")
	@Test
	public void findAllWithPaging() {
		//given
		Apartment apartment1 = new Apartment("34302", "서울특별시", "강남구", "개포동", "22",
			"43", "0002", "테스트 아파트", 1988, "언주로 103");
		Apartment apartment2 = new Apartment("43254", "테스트", "강남구", "개포동", "22",
			"43", "0002", "테스트 아파트", 1988, "언주로 103");
		Apartment apartment3 = new Apartment("43252", "테스트", "강남구", "개포동", "22",
			"43", "0002", "테스트 아파트", 1988, "언주로 103");
		//when
		apartmentRepository.save(apartment1);
		apartmentRepository.save(apartment2);
		apartmentRepository.save(apartment3);

		List<Apartment> allWithPaging = apartmentRepository.findAll(PageRequest.of(0, 3)).getContent();
		assertThat(allWithPaging.size()).isEqualTo(3);
	}
}
