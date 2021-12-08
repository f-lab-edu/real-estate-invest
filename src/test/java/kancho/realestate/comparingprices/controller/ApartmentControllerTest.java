package kancho.realestate.comparingprices.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class ApartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApartmentController apartmentController;

	private String apartmentInfo;

	@BeforeEach
	public void setUpApartmentInfo(){
		//given
		apartmentInfo = createApartmentInfo( "12345", "서울", "강남구", "역삼동",
			"23-23", "3423", "1230", "test name", 1994,
			"test road");
	}

	@Test
	public void 아파트_등록() throws Exception {

		// when
		ResultActions result = createApartment("/apartments", apartmentInfo);

		// then
		asserCreatedResponse(result);
	}

	@Test
	public void 아파트_목록_조회() throws Exception {
		//given
		createApartment("/apartments", apartmentInfo);

		// when
		ResultActions result = getApartments("/apartments");

		//then
		assertOkResponse(result);

	}

	public static String createApartmentInfo(String regionalCode, String city, String gu, String dong, String jibun,
		String bonbun, String bubun, String apartmentName, int buildYear, String roadAddress) {
		HashMap<String, String> bodyContent = new HashMap<>();
		bodyContent.put("regionalCode", regionalCode);
		bodyContent.put("city", city);
		bodyContent.put("gu", gu);
		bodyContent.put("dong", dong);
		bodyContent.put("jibun", jibun);
		bodyContent.put("bonbun", bonbun);
		bodyContent.put("bubun", bubun);
		bodyContent.put("apartmentName", apartmentName);
		bodyContent.put("buildYear", String.valueOf(buildYear));
		bodyContent.put("roadAddress", roadAddress);

		return String.valueOf(new JSONObject(bodyContent));
	}

	public ResultActions getApartments(String path) throws Exception {
		return mockMvc.perform(get(path)
			.contentType(MediaType.APPLICATION_JSON));

	}

	private ResultActions createApartment(String path, String content) throws Exception {
		return mockMvc.perform(post(path)
			.content(content)
			.contentType(MediaType.APPLICATION_JSON));
	}

	private void assertOkResponse(ResultActions result) throws Exception {
		result.andExpect(status().isOk());
	}

	private void asserCreatedResponse(ResultActions result) throws Exception {
		result.andExpect(status().isCreated());
	}
}