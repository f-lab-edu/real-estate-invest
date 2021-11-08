package kancho.realestate.comparingprices.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.servlet.UnavailableException;
import javax.servlet.http.Cookie;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.exception.DuplicateLoginException;
import kancho.realestate.comparingprices.exception.DuplicateUserAccountException;
import kancho.realestate.comparingprices.exception.IdNotExistedException;
import kancho.realestate.comparingprices.exception.InvalidLoginParameterException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserController userController;

	@Test
	@Transactional
	public void join_회원가입_성공() throws Exception {
		String serializedBody = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	public String serailizedTesterBody(String id, String password) {
		HashMap<String, String> bodyContent = new HashMap<>();
		bodyContent.put("id", id);
		bodyContent.put("password", password);
		return String.valueOf(new JSONObject(bodyContent));
	}

	@Test
	@Transactional
	public void join_회원가입_중복_오류() throws Exception {

		String serializedBody = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertThat(result.getResolvedException()).isInstanceOf(DuplicateUserAccountException.class));
	}

	@Test
	@Transactional
	public void login_로그인_성공() throws Exception {

		String serializedBody = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		mockMvc.perform(post("/login")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	@Test
	@Transactional
	public void login_로그인_없는계정_오류() throws Exception {

		String serializedBody = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		String serializedBodyLogin = serailizedTesterBody("jerry9","1234");

		mockMvc.perform(post("/login")
			.content(serializedBodyLogin)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertThat(result.getResolvedException()).isInstanceOf(IdNotExistedException.class));
	}

	@Test
	@Transactional
	public void login_로그인_비밀번호_오류() throws Exception {

		String serializedBody = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		String serializedBodyLogin = serailizedTesterBody("jerry9","123");

		mockMvc.perform(post("/login")
			.content(serializedBodyLogin)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertThat(result.getResolvedException()).isInstanceOf(IdNotExistedException.class));
	}

	@Test
	@Transactional
	public void login_로그인한_사람이_같은_아이디로_로그인요청() throws Exception {

		String serializedBody = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		MvcResult mvcResult = mockMvc.perform(post("/login")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookie = mvcResult.getResponse().getCookie("SESSION");

		mockMvc.perform(post("/login")
			.content(serializedBody)
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(DuplicateLoginException.class));
	}

	@Test
	@Transactional
	public void login_로그인한_사람이_다른_아이디로_로그인요청() throws Exception {

		String serializedBodyOne = serailizedTesterBody("jerry","1234");

		// 회원가입
		mockMvc.perform(post("/join")
			.content(serializedBodyOne)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		String serializedBodyOther = serailizedTesterBody("adam","13523");

		mockMvc.perform(post("/join")
			.content(serializedBodyOther)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		// 로그인
		MvcResult loginResult = mockMvc.perform(post("/login")
			.content(serializedBodyOne)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookie = loginResult.getResponse().getCookie("SESSION");

		// 로그인해서 세션이 있는 상태에서 다른 아이디로 로그인
		MvcResult loginResultOther = mockMvc.perform(post("/login")
			.content(serializedBodyOther)
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookieOther = loginResultOther.getResponse().getCookie("SESSION");

		// 세션키 바뀜 검증
		assertThat(cookie).isNotEqualTo(cookieOther);

		// 세션키 없어서 인증 오류 확인
		mockMvc.perform(get("/my-estate/test")
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AuthenticationException.class));
	}

	@Test
	@Transactional
	public void join_login_로그인_상태에서_회원가입_요청_예외처리() throws Exception {

		String serializedBodyOne = serailizedTesterBody("jerry","1234");

		mockMvc.perform(post("/join")
			.content(serializedBodyOne)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		MvcResult mvcResult = mockMvc.perform(post("/login")
			.content(serializedBodyOne)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookie = mvcResult.getResponse().getCookie("SESSION");

		String serializedBodyOther = serailizedTesterBody("adam","13523");

		mockMvc.perform(post("/join")
			.content(serializedBodyOther)
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(IllegalStateException.class));
	}
}