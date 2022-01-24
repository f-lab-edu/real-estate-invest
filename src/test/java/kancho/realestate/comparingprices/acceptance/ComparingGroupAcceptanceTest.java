package kancho.realestate.comparingprices.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kancho.realestate.comparingprices.acceptance.testFixtures.ApartmentAcceptanceFixture;
import kancho.realestate.comparingprices.acceptance.testFixtures.ComparingGroupAcceptanceFixture;
import kancho.realestate.comparingprices.acceptance.testFixtures.UserAcceptanceFixture;
import kancho.realestate.comparingprices.common.RestAssuredTest;
import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;

class ComparingGroupAcceptanceTest extends RestAssuredTest {

	private ResponseUserDto user;


	@BeforeEach
	public void setUp() {
		super.setUp();
		//given
		user = UserAcceptanceFixture.회원가입_요청(new RequestUserDto("tom ford", "12345678")).response()
			.jsonPath().getObject("result", ResponseUserDto.class);
	}

	@DisplayName("비교 그룹 등록")
	@Test
	void createComparingGroup() {

		// when
		ExtractableResponse<Response> 그룹_등록_결과 = ComparingGroupAcceptanceFixture.그룹_등록(
			new RequestComparingGroupDto(user.getUserId(), "1순위그룹"));

		// then
		assertThat(그룹_등록_결과.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@DisplayName("비교 그룹에 아파트 추가")
	@Test
	void addGroupItem() {
		// given
		ResponseApartmentDto apartment1 = ApartmentAcceptanceFixture.아파트_등록_응답_객체(
			new RequestApartmentDto("12345", "서울", "강남구", "역삼동",
				"23-23", "3423", "1230", "test name1", 1994,
				"test road1"));


		ResponseComparingGroupDto group = ComparingGroupAcceptanceFixture.그룹_등록_객체(
			new RequestComparingGroupDto(user.getUserId(), "1순위그룹"));

		//when
		ExtractableResponse<Response> 그룹에_아파트_추가_결과
			= ComparingGroupAcceptanceFixture.그룹에_아파트_추가(group.getId(),new RequestGroupItemDto(apartment1.getId()));

		//then
		assertThat(그룹에_아파트_추가_결과.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}
}
