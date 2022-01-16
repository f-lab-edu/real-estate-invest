package kancho.realestate.comparingprices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kancho.realestate.comparingprices.domain.model.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

	// idx_unique_apartment 인덱스 사용하도록 관련 필드만 select절 포함
	@Query("select a from Apartment a " +
		"where a.regionalCode = :regionalCode " +
		"and a.dong = :dong " +
		"and a.jibun= :jibun " +
		"and a.apartmentName= :apartmentName ")
	Optional<Apartment> findApartByDistinctFields(@Param("regionalCode") String regionalCode,
		@Param("dong") String dong, @Param("jibun") String jibun, @Param("apartmentName") String apartmentName);
}
