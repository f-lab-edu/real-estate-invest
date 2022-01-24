package kancho.realestate.comparingprices.acceptance.testFixtures;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;

public class UserAcceptanceFixture {
	public static ExtractableResponse<Response> 회원가입_요청(RequestUserDto requestUserDto) {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(requestUserDto)
			.when().post("/join")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 회원가입_요청(RequestUserDto requestUserDto, Cookie sessionCookie) {
		return RestAssured
			.given().cookie(sessionCookie).log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(requestUserDto)
			.when().post("/join")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 로그인_요청(RequestUserDto requestUserDto) {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(requestUserDto)
			.when().post("/login")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 로그인_요청(RequestUserDto requestUserDto, Cookie sessionCookie) {

		return RestAssured
			.given().cookie(sessionCookie).log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(requestUserDto)
			.when().post("/login")
			.then().log().all()
			.extract();
	}


	public static Cookie 세션ID_추출(ExtractableResponse<Response> response){
		return response.detailedCookie("SESSION");
	}
}
