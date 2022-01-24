package kancho.realestate.comparingprices.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.Cookie;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.acceptance.testFixtures.UserAcceptanceFixture;
import kancho.realestate.comparingprices.acceptance.testFixtures.UserEstateAcceptanceFixture;
import kancho.realestate.comparingprices.common.RestAssuredTest;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;

class UserEstateAcceptanceTest extends RestAssuredTest {

	@Test
	public void test_세션인증_성공_테스트() {

		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester","12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);
		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1);
		Cookie sessionCookie = UserAcceptanceFixture.세션ID_추출(로그인_요청_결과);

		//when
		ExtractableResponse<Response> 인증_테스트_결과 = UserEstateAcceptanceFixture.인증_테스트(sessionCookie);

		//then
		assertThat(인증_테스트_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void test_세션없이_요청_예외() {
		//when
		ExtractableResponse<Response> 인증_테스트_결과 = UserEstateAcceptanceFixture.인증_테스트("");

		//then
		assertThat(인증_테스트_결과.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}
}
