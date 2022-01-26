package kancho.realestate.comparingprices.scheduler.util;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDetailDto;
import kancho.realestate.comparingprices.domain.vo.ApartmentDetail;
import kancho.realestate.comparingprices.exception.InvalidDealYearAndMonthException;
import kancho.realestate.comparingprices.exception.InvalidRegionalCodeException;
import kancho.realestate.comparingprices.scheduler.util.ApartmentApiClient;

@SpringBootTest
class ApartmentApiClientTest {

	@Autowired
	private ApartmentApiClient apartmentApiClient;
	private final String existingRegionalCode = "11110";
	private final String notExistingRegionalCode = "4444444";
	private final int correctDealYear = 2015;
	private final int correctDealMonth = 12;
	private final int notExistingDealMonth = 44;
	private final int futureDealYear = 9999;

	@Test
	void 존재하는_지역코드와_올바른_계약날짜를_입력하면_아파트정보_요청_성공() throws JAXBException, IOException {
		RequestApartmentDetailDto requestDto = new RequestApartmentDetailDto(existingRegionalCode, correctDealYear,
			correctDealMonth);

		List<ApartmentDetail> apartmentDetailList = apartmentApiClient.getApartmentDetails(requestDto);

		assertThat(apartmentDetailList.get(0).toString())
			.contains(existingRegionalCode);
	}

	@Test
	void 존재하지_않는_지역코드를_입력하면_예외_발생() {
		assertThatThrownBy(() -> {
			RequestApartmentDetailDto requestDto = new RequestApartmentDetailDto(notExistingRegionalCode,
				correctDealYear, correctDealMonth);
		}).isInstanceOf(InvalidRegionalCodeException.class);
	}

	@Test
	void 달력에_존재하지_않는_계약월을_입력하면_예외_발생() {
		assertThatThrownBy(() -> {
			RequestApartmentDetailDto requestDto = new RequestApartmentDetailDto(existingRegionalCode, correctDealYear,
				notExistingDealMonth);
		}).isInstanceOf(InvalidDealYearAndMonthException.class);
	}

	@Test
	void 현재_보다_미래의_계약날짜를_입력하면_예외_발생() {
		// 현재 연도와 동일한 연도, 미래의 월을 입력해도 동일한 오류가 발생한다.
		assertThatThrownBy(() -> {
			RequestApartmentDetailDto requestDto = new RequestApartmentDetailDto(existingRegionalCode, futureDealYear,
				correctDealMonth);
		}).isInstanceOf(InvalidDealYearAndMonthException.class);
	}

}
