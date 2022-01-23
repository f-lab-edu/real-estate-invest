package kancho.realestate.comparingprices.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kancho.realestate.comparingprices.domain.model.SoaringPrice;

class SoaringPricesServiceTest extends ServiceTest {

	@Autowired
	SoaringPricesService soaringPricesService;

	@Test
	void 상승률이_높은_부동산_조회(){
		List<SoaringPrice> soaringPriceList = soaringPricesService.getSoaringPrices(SoaringPrice.Unit.PERCENT);
		assertThat(soaringPriceList).isNotNull(); // 값이 존재
	}

	@Test
	void 상승금액이_높은_부동산_조회(){
		List<SoaringPrice> soaringPriceList = soaringPricesService.getSoaringPrices(SoaringPrice.Unit.WON);
		assertThat(soaringPriceList).isNotNull(); // 값이 존재
	}
}
