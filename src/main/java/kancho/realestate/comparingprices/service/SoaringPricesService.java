package kancho.realestate.comparingprices.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.model.SoaringPrices;
import kancho.realestate.comparingprices.repository.SoaringPricesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SoaringPricesService {

	private final SoaringPricesRepository soaringPricesRepository;

	// 매일 12시에 새로 상위 10개를 insert
	private static final PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
	// 금액 상승률이 가장 높은 부동산 정보 가져오기
	public List<SoaringPrices> getSoaringPrices(SoaringPrices.Unit priceDifferenceUnit) {
		return soaringPricesRepository.findSoaringPricesByPriceDifferenceUnit(priceDifferenceUnit.name(),
				pageRequest).getContent();
	}
}
