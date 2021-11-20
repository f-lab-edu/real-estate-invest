package kancho.realestate.comparingprices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.model.SoaringPrices;
import kancho.realestate.comparingprices.repository.SoaringPricesMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SoaringPricesService {

	private final SoaringPricesMapper soaringPricesMapper;

	// 금액 상승률이 가장 높은 부동산 정보 가져오기
	public List<SoaringPrices> getSoaringPrices(SoaringPrices.Unit priceDifferenceUnit) {
		return soaringPricesMapper.selectSoaringPricesByUnit(priceDifferenceUnit.name());
	}
}
