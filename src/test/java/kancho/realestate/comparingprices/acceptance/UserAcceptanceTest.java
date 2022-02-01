package kancho.realestate.comparingprices.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.acceptance.testFixtures.UserAcceptanceFixture;
import kancho.realestate.comparingprices.common.RestAssuredTest;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;

class UserAcceptanceTest extends RestAssuredTest {

	@Test
	public void join_회원가입_성공() {
		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");

		//when
		ExtractableResponse<Response> 회원가입_요청_결과 = UserAcceptanceFixture.회원가입_요청(테스터1);

		//then
		assertThat(회원가입_요청_결과.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void join_회원가입_중복_오류() {

		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);

		//when
		ExtractableResponse<Response> 회원가입_요청_결과 = UserAcceptanceFixture.회원가입_요청(테스터1);

		//then
		assertThat(회원가입_요청_결과.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void login_로그인_성공() throws Exception {
		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);

		//when
		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1);

		//then
		assertThat(로그인_요청_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void login_로그인_없는계정_오류() {
		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);
		RequestUserDto 테스터2 = new RequestUserDto("tester2", "12345657567zdasdas");

		//when
		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터2);

		//then
		assertThat(로그인_요청_결과.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	public void login_로그인_비밀번호_오류() {

		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);
		RequestUserDto 테스터1_비밀번호오류 = new RequestUserDto("tester", "2sdfwer2312");

		//when
		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1_비밀번호오류);

		//then
		assertThat(로그인_요청_결과.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	public void login_로그인한_사람이_같은_아이디로_로그인요청() {
		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);
		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1);
		//when
		ExtractableResponse<Response> 동일_아이디로_로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1,
			UserAcceptanceFixture.세션ID_추출(로그인_요청_결과));

		//then
		assertThat(동일_아이디로_로그인_요청_결과.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	public void login_로그인한_사람이_다른_아이디로_로그인요청() {
		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);
		RequestUserDto 테스터2 = new RequestUserDto("tester2", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터2);

		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1);
		//when
		ExtractableResponse<Response> 다른_아이디로_로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터2,
			UserAcceptanceFixture.세션ID_추출(로그인_요청_결과));

		//then
		assertThat(다른_아이디로_로그인_요청_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void 로그인_상태에서_회원가입_요청_예외처리() {
		//given
		RequestUserDto 테스터1 = new RequestUserDto("tester", "12345657567zdasdas");
		UserAcceptanceFixture.회원가입_요청(테스터1);
		ExtractableResponse<Response> 로그인_요청_결과 = UserAcceptanceFixture.로그인_요청(테스터1);

		RequestUserDto 테스터2 = new RequestUserDto("tester2", "12345657567zdasdas");
		//when


		ExtractableResponse<Response> 로그인중_회원가입_요청_결과 = UserAcceptanceFixture.회원가입_요청(테스터2,
			UserAcceptanceFixture.세션ID_추출(로그인_요청_결과));

		//then
		assertThat(로그인중_회원가입_요청_결과.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}
}
