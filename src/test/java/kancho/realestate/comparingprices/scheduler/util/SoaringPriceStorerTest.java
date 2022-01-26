package kancho.realestate.comparingprices.scheduler.util;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestSoaringPriceDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentPriceDto;
import kancho.realestate.comparingprices.domain.model.SoaringPrice;

@SpringBootTest
@Transactional
class SoaringPriceStorerTest {

	@Autowired
	SoaringPriceStorer soaringPriceStorer;

	static ResponseApartmentPriceDto latestApartmnetPrice;
	static ResponseApartmentPriceDto oldApartmentPrice;
	static ResponseApartmentPriceDto otherLatestApartmnetPrice;

	static ResponseApartmentPriceDto cheapestApartmentPriceFromLastYear;
	static ResponseApartmentPriceDto expensiveApartmentPriceFromLastYear;

	static List<ResponseApartmentPriceDto> apartmentPriceList;

	static Map<String, ResponseApartmentPriceDto> latestPriceMap;
	static Map<String, ResponseApartmentPriceDto> cheapestPriceMap;

	@BeforeAll
	public static void setUp() {
		int dealYear = LocalDate.now().getYear();
		latestApartmnetPrice = new ResponseApartmentPriceDto(1, new BigDecimal("1.1"), dealYear, 12, 31, 100, 10);
		oldApartmentPrice = new ResponseApartmentPriceDto(1, new BigDecimal("1.1"), dealYear, 11, 30, 100, 10);

		cheapestApartmentPriceFromLastYear = new ResponseApartmentPriceDto(1, new BigDecimal("1.1"), dealYear - 1, 11,
			20, 1, 10);
		expensiveApartmentPriceFromLastYear = new ResponseApartmentPriceDto(1, new BigDecimal("1.1"), dealYear - 1, 11,
			30, 999, 10);

		apartmentPriceList = new ArrayList<>();
		apartmentPriceList.add(latestApartmnetPrice);
		apartmentPriceList.add(oldApartmentPrice);
		apartmentPriceList.add(cheapestApartmentPriceFromLastYear);
		apartmentPriceList.add(expensiveApartmentPriceFromLastYear);

		otherLatestApartmnetPrice = new ResponseApartmentPriceDto(1, new BigDecimal("44.4"), dealYear - 1, 11, 30, 999,
			10);

		latestPriceMap = new HashMap<>();
		latestPriceMap.put(makeApartmentUniqueId(latestApartmnetPrice), latestApartmnetPrice);
		latestPriceMap.put(makeApartmentUniqueId(otherLatestApartmnetPrice), otherLatestApartmnetPrice);

		cheapestPriceMap = new HashMap<>();
		cheapestPriceMap.put(makeApartmentUniqueId(cheapestApartmentPriceFromLastYear),
			cheapestApartmentPriceFromLastYear);
	}

	private static String makeApartmentUniqueId(ResponseApartmentPriceDto apartmentPrice) {
		StringBuilder sb = new StringBuilder();
		return sb.append(apartmentPrice.getApartmentId())
			.append("-")
			.append(apartmentPrice.getAreaForExclusiveUse())
			.toString();
	}

	@Test
	void 작년부터_올해까지_가격_반환() {
		List<ResponseApartmentPriceDto> apartmentPriceList = soaringPriceStorer.getApartmenstPriceFromLastyear();
		assertThat(apartmentPriceList).isNotEmpty();
	}

	@Test
	void 최신_가격을_추출하면_최신_가격이_포함() {
		Map<String, ResponseApartmentPriceDto> latestPriceMap = soaringPriceStorer.getLatestApartmentPrices(
			apartmentPriceList);
		String latestPriceId = makeApartmentUniqueId(latestApartmnetPrice);
		assertThat(latestPriceMap).contains(entry(latestPriceId, latestApartmnetPrice));
	}

	@Test
	void 최저가_가격을_추출하면_최저가_가격이_포함() {
		Map<String, ResponseApartmentPriceDto> cheapestPriceMap = soaringPriceStorer.getCheapestPriceListFromLastYear(
			apartmentPriceList);
		String cheapestPriceId = makeApartmentUniqueId(cheapestApartmentPriceFromLastYear);
		assertThat(cheapestPriceMap).contains(entry(cheapestPriceId, cheapestApartmentPriceFromLastYear));
	}

	@Test
	void 최저가_가격을_추출하면_최저가_보다_높은_가격은_불포함() {
		Map<String, ResponseApartmentPriceDto> cheapestPriceMap = soaringPriceStorer.getCheapestPriceListFromLastYear(
			apartmentPriceList);
		String expensivePriceId = makeApartmentUniqueId(expensiveApartmentPriceFromLastYear);
		assertThat(cheapestPriceMap).doesNotContain(entry(expensivePriceId, expensiveApartmentPriceFromLastYear));
	}

	@Test
	void 가격_상승률을_구하면_최고_상승률_10가지만_반환() {
		//given
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ResponseApartmentPriceDto latestPriceDto = new ResponseApartmentPriceDto(i, new BigDecimal("1.1"), 2022, 1,
				i + 1, 1000, 10);
			ResponseApartmentPriceDto cheapestPriceDto = new ResponseApartmentPriceDto(i, new BigDecimal("1.1"), 2022,
				1, i, 10, 10);
			latestPriceMap.put(makeApartmentUniqueId(latestPriceDto), latestPriceDto);
			cheapestPriceMap.put(makeApartmentUniqueId(cheapestPriceDto), cheapestPriceDto);
		});

		//when
		List<RequestSoaringPriceDto> soaringPriceByWonList = soaringPriceStorer.getSoaringPriceList(latestPriceMap,
			cheapestPriceMap, SoaringPrice.Unit.WON);
		List<RequestSoaringPriceDto> soaringPriceByPercentList = soaringPriceStorer.getSoaringPriceList(latestPriceMap,
			cheapestPriceMap, SoaringPrice.Unit.PERCENT);

		//then
		assertThat(soaringPriceByWonList.size()).
			isEqualTo(soaringPriceByPercentList.size())
			.isEqualTo(10);
	}

	@Test
	void 급상승_가격정보를_저장하면_저장한_데이터_수만큼_반환() {
		//given
		List<RequestSoaringPriceDto> soaringPriceByWonList = new ArrayList<>();
		List<RequestSoaringPriceDto> soaringPriceByPercentList = new ArrayList<>();
		IntStream.rangeClosed(1, 10).forEach(i -> {
			soaringPriceByWonList.add(new RequestSoaringPriceDto(i, "1,1", LocalDate.of(2022, 1, 24),
				100, LocalDate.of(2022, 1, 25), 120, SoaringPrice.Unit.WON, 20));
			soaringPriceByPercentList.add(new RequestSoaringPriceDto(i, "1,1", LocalDate.of(2022, 1, 24),
				100, LocalDate.of(2022, 1, 25), 120, SoaringPrice.Unit.PERCENT, 120));
		});

		//when
		List<SoaringPrice> saved = soaringPriceStorer.saveSoaringPriceList(soaringPriceByWonList, soaringPriceByPercentList);

		//then
		assertThat(saved.size()).isEqualTo(20);
	}
}
