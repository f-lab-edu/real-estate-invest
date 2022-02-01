package kancho.realestate.comparingprices.acceptance.testFixtures;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;

public class ApartmentAcceptanceFixture {

	public static ExtractableResponse<Response> 아파트_등록(RequestApartmentDto apartmentDto) {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(apartmentDto)
			.when().post("/apartments")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 아파트_목록_조회() {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/apartments")
			.then().log().all()
			.extract();
	}

	public static ResponseApartmentDto 아파트_등록_응답_객체(RequestApartmentDto apartmentDto) {
		return 아파트_등록(apartmentDto).response()
			.jsonPath().getObject("result", ResponseApartmentDto.class);
	}
}
