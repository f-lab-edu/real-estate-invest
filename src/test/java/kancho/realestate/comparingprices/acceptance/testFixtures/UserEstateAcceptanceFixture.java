package kancho.realestate.comparingprices.acceptance.testFixtures;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class UserEstateAcceptanceFixture {

	public static ExtractableResponse<Response> 인증_테스트(String session) {
		return RestAssured
			.given().sessionId(session).log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/my-estate/test")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 인증_테스트(Cookie cookie) {
		return RestAssured
			.given().log().all()
			.cookie(cookie)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/my-estate/test")
			.then().log().all()
			.extract();
	}
}
