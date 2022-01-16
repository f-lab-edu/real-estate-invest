package kancho.realestate.comparingprices.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kancho.realestate.comparingprices.domain.model.SoaringPrices;

class SoaringPricesServiceTest extends ServiceTest {

	@Autowired
	SoaringPricesService soaringPricesService;

	@Test
	void 상승률이_높은_부동산_조회(){
		List<SoaringPrices> soaringPricesList = soaringPricesService.getSoaringPrices(SoaringPrices.Unit.PERCENT);
		assertThat(soaringPricesList).isNotNull(); // 값이 존재
	}

	@Test
	void 상승금액이_높은_부동산_조회(){
		List<SoaringPrices> soaringPricesList = soaringPricesService.getSoaringPrices(SoaringPrices.Unit.WON);
		assertThat(soaringPricesList).isNotNull(); // 값이 존재
	}
}
