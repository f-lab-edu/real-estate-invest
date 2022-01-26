package kancho.realestate.comparingprices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kancho.realestate.comparingprices.domain.model.ApartmentPrice;

public interface ApartmentPriceRepository extends JpaRepository<ApartmentPrice, Long> {
	List<ApartmentPrice> findByDealYearBetween(int start, int end);
}
