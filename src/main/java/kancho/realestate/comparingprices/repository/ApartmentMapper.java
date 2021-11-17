package kancho.realestate.comparingprices.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import kancho.realestate.comparingprices.domain.model.Apartment;

@Mapper
public interface ApartmentMapper {

	void save(Apartment apartment);

	List<Apartment> findAll();

	// idx_unique_apartment 인덱스 사용하도록 관련 필드만 select절 포함
	Optional<Apartment> findByRegionalCodeAndDongAndJibunAndApartmentName(Apartment apartment);
}
