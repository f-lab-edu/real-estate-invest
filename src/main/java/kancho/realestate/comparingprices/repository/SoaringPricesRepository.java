package kancho.realestate.comparingprices.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import kancho.realestate.comparingprices.domain.model.SoaringPrice;

public interface SoaringPricesRepository extends JpaRepository<SoaringPrice, Long> {

	// count 쿼리는 필요없으므로 Slice로 받음. jpql에서는 limit 사용 못함. Pageable 사용해야함
	Slice<SoaringPrice> findSoaringPricesByPriceDifferenceUnit(String priceDifferenceUnit,
		Pageable pageable);
}
