package kancho.realestate.comparingprices.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.DataInput;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.coyote.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class ComparingGroupControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private ResponseUserDto user;
	private ResponseApartmentDto apartment1;
	private ResponseApartmentDto apartment2;

	@BeforeEach
	void setUp() throws Exception {
		// given
		String userInfo = UserControllerTest.createUserInfo("tom ford", "12345678");
		user = saveUser(userInfo);

		String apartmentInfo1 = ApartmentControllerTest.createApartmentInfo("12345", "??????", "?????????", "?????????",
			"23-23", "3423", "1230", "test name1", 1994,
			"test road1");
		apartment1 = saveApartment(apartmentInfo1);

		String apartmentInfo2 = ApartmentControllerTest.createApartmentInfo("23431", "?????????", "?????????", "?????????",
			"23-23", "3423", "1230", "test name2", 1994,
			"test road2");
		apartment2 = saveApartment(apartmentInfo2);
	}

	@DisplayName("?????? ?????? ??????")
	@Test
	void createComparingGroup() throws Exception {

		// when
		String groupName="1????????????";
		MvcResult result = requestPost("/comparing-group", createComparingGroup(user.getUserNo(), groupName),
			status().isCreated());

		// then
		ResponseComparingGroupDto groupDto =getContentFromResult(result.getResponse().getContentAsString(),
			new TypeReference<SuccessReponseDto<ResponseComparingGroupDto>>() {}).getResult();

		assertThat(groupDto.getId()).isNotNull();
		assertThat(groupDto.getName()).isEqualTo(groupName);
	}

	@DisplayName("?????? ?????? ??????")
	@Test
	void addGroupItem() throws Exception {

		// given
		MvcResult result = requestPost("/comparing-group", createComparingGroup(user.getUserNo(), "1????????????"),
			status().isCreated());

		ResponseComparingGroupDto groupDto =getContentFromResult(result.getResponse().getContentAsString(),
			new TypeReference<SuccessReponseDto<ResponseComparingGroupDto>>() {}).getResult();

		// when
		MvcResult resultItem = requestPost("/comparing-group/" + groupDto.getId() + "/groupItems",
			createGroupItem(groupDto.getId(), apartment1.getId()),
			status().isCreated());

		// then
		ResponseGroupItemDto groupItemDto = getContentFromResult(resultItem.getResponse().getContentAsString(),
			new TypeReference<SuccessReponseDto<ResponseGroupItemDto>>() {
			}).getResult();

		assertThat(groupItemDto.getId()).isNotNull();
		assertThat(groupItemDto.getGroupId()).isEqualTo(groupDto.getId());
		assertThat(groupItemDto.getApartmentId()).isEqualTo(apartment1.getId());
	}

	public String createComparingGroup(Long userNo, String name) {
		HashMap<String, String> bodyContent = new HashMap<>();
		bodyContent.put("userNo", String.valueOf(userNo));
		bodyContent.put("name", name);

		return String.valueOf(new JSONObject(bodyContent));
	}

	public String createGroupItem(Long groupId, Long apartmentId) {
		HashMap<String, String> bodyContent = new HashMap<>();
		bodyContent.put("groupId", String.valueOf(groupId));
		bodyContent.put("apartmentId", String.valueOf(apartmentId));

		return String.valueOf(new JSONObject(bodyContent));
	}

	private ResponseUserDto saveUser(String userInfo) throws Exception {
		MvcResult result = requestPost("/join", userInfo, status().isCreated());
		return getContentFromResult(result.getResponse().getContentAsString(),
			new TypeReference<SuccessReponseDto<ResponseUserDto>>() {
			}).getResult();
	}

	private ResponseApartmentDto saveApartment(String apartmentInfo) throws Exception {
		MvcResult result = requestPost("/apartments", apartmentInfo, status().isCreated());
		return getContentFromResult(result.getResponse().getContentAsString(),
			new TypeReference<SuccessReponseDto<ResponseApartmentDto>>() {
			}).getResult();
	}

	private <T> T getContentFromResult(String contentAsString, TypeReference<T> type) throws
		JsonProcessingException {
		return objectMapper.readValue(contentAsString, type);
	}

	public MvcResult requestPost(String requestPath, String contentBody, ResultMatcher resultMatcher) throws Exception {
		return mockMvc.perform(post(requestPath)
				.content(contentBody)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(resultMatcher)
			.andReturn();
	}

}