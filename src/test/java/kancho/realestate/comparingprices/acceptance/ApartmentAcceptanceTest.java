package kancho.realestate.comparingprices.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.acceptance.testFixtures.ApartmentAcceptanceFixture;
import kancho.realestate.comparingprices.common.RestAssuredTest;
import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;

class ApartmentAcceptanceTest extends RestAssuredTest {

	@Test
	public void 아파트_등록() {

		//when
		ExtractableResponse<Response> 아파트_등록_결과 = ApartmentAcceptanceFixture.아파트_등록(
			new RequestApartmentDto("12345", "서울", "강남구", "역삼동",
				"23-23", "3423", "1230", "test name1", 1994,
				"test road1"));

		//then
		assertThat(아파트_등록_결과.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void 아파트_목록_조회() {

		//given
		ResponseApartmentDto 아파트1 = ApartmentAcceptanceFixture.아파트_등록_응답_객체(
			new RequestApartmentDto("12345", "서울", "강남구", "역삼동",
				"23-23", "3423", "1230", "test name1", 1994,
				"test road1"));

		ResponseApartmentDto 아파트2 = ApartmentAcceptanceFixture.아파트_등록_응답_객체(
			new RequestApartmentDto("52353", "서울2", "강남구2", "역삼동2",
				"23-234", "34235", "12301", "test name2", 1996,
				"test road2"));

		//when
		ExtractableResponse<Response> 아파트_목록_조회_결과 = ApartmentAcceptanceFixture.아파트_목록_조회();

		//then
		assertThat(아파트_목록_조회_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
		아파트_조회_결과_비교(아파트_목록_조회_결과, Arrays.asList(아파트1,아파트2));
	}

	private void 아파트_조회_결과_비교(ExtractableResponse<Response> response, List<ResponseApartmentDto> expectApartments) {
		List<ResponseApartmentDto> 조회된_아파트_목록 = response.response()
			.jsonPath()
			.getList("result", ResponseApartmentDto.class);
		assertThat(조회된_아파트_목록.size()).isEqualTo(expectApartments.size());
		assertThat(조회된_아파트_목록).containsAll(expectApartments);
	}
}
