package kancho.realestate.utils.api.storeaprtment;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.ApartmentDetail;

/*
private 메서드를 테스트하기 위한 방법
1. 리플렉션 https://www.crocus.co.kr/1665
*/

@Transactional
@SpringBootTest
class ApartmentApiClientTest {

	@Autowired
	ApartmentApiClient apartmentDetailRequest;

	@Test
	void 아파트정보_가져오기() throws IOException, JAXBException {
		//given
		String lawdCd = "11110";	// 지역 번호
		String dealYMD = "201512";	// 계약 월

		// when
		List<ApartmentDetail> apartmentDetailList = apartmentDetailRequest.getApartmentDetails(lawdCd, dealYMD);

		// then
		ApartmentDetail detail = apartmentDetailList.get(0);
		assertThat(detail.getDealYear()).isEqualTo(2015);
		assertThat(detail.getDealMonth()).isEqualTo(12);
		assertThat(apartmentDetailList.size()).isEqualTo(49);
	}

	@Test
	void 잘못된_요청인자_오류_테스트() throws JAXBException, IOException {
		//given
		String lawdCd = "444";	// 지역 번호
		String dealYMD = "444";	// 계약 월

		// when
		List<ApartmentDetail> apartmentDetailList = apartmentDetailRequest.getApartmentDetails(lawdCd, dealYMD);

		// then
		ApartmentDetail detail = apartmentDetailList.get(0);
		assertThat(apartmentDetailList.size()).isEqualTo(0);
	}

}