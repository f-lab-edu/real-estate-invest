package kancho.realestate.comparingprices.acceptance.testFixtures;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;

public class ComparingGroupAcceptanceFixture {

	public static ExtractableResponse<Response> 그룹_등록(RequestComparingGroupDto comparingGroupDto) {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(comparingGroupDto)
			.when().post("/comparing-group")
			.then().log().all()
			.extract();
	}

	public static ResponseComparingGroupDto 그룹_등록_객체(RequestComparingGroupDto comparingGroupDto) {
		return 그룹_등록(comparingGroupDto).response()
			.jsonPath().getObject("result", ResponseComparingGroupDto.class);
	}

	public static ExtractableResponse<Response> 그룹에_아파트_추가(Long groupId, RequestGroupItemDto requestGroupItemDto) {
		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(requestGroupItemDto)
			.when().post("/comparing-group/{groupId}/groupItems",groupId)
			.then().log().all()
			.extract();
	}
}
