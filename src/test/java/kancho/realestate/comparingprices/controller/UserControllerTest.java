package kancho.realestate.comparingprices.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.exception.DuplicateLoginException;
import kancho.realestate.comparingprices.exception.DuplicateUserAccountException;
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

		HashMap<String,String> bodyContent = new HashMap<>();
		bodyContent.put("id","jerry");
		bodyContent.put("password","1234");
		String serializedBody = String.valueOf(new JSONObject(bodyContent));

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	@Test
	@Transactional
	public void join_회원가입_중복_오류() throws Exception {

		HashMap<String,String> bodyContent = new HashMap<>();
		bodyContent.put("id","jerry");
		bodyContent.put("password","1234");
		String serializedBody = String.valueOf(new JSONObject(bodyContent));

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		mockMvc.perform(post("/join")
			.content(serializedBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(DuplicateUserAccountException.class));
	}

	@Test
	@Transactional
	public void login_로그인_성공() throws Exception {

		HashMap<String,String> bodyContent = new HashMap<>();
		bodyContent.put("id","jerry");
		bodyContent.put("password","1234");
		String serializedBody = String.valueOf(new JSONObject(bodyContent));

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

		HashMap<String,String> signupBodyContent = new HashMap<>();
		signupBodyContent.put("id","jerry");
		signupBodyContent.put("password","1234");
		String serializedSignupBody = String.valueOf(new JSONObject(signupBodyContent));

		mockMvc.perform(post("/join")
			.content(serializedSignupBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		HashMap<String,String> loginBodyContent = new HashMap<>();
		loginBodyContent.put("id","jerry9");
		loginBodyContent.put("password","1234");
		String serializedLoginBody = String.valueOf(new JSONObject(loginBodyContent));

		mockMvc.perform(post("/login")
			.content(serializedLoginBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(InvalidLoginParameterException.class));
	}

	@Test
	@Transactional
	public void login_로그인_비밀번호_오류() throws Exception {

		HashMap<String,String> signupBodyContent = new HashMap<>();
		signupBodyContent.put("id","jerry");
		signupBodyContent.put("password","1234");
		String serializedSignupBody = String.valueOf(new JSONObject(signupBodyContent));

		mockMvc.perform(post("/join")
			.content(serializedSignupBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		HashMap<String,String> loginBodyContent = new HashMap<>();
		loginBodyContent.put("id","jerry");
		loginBodyContent.put("password","123");
		String serializedLoginBody = String.valueOf(new JSONObject(loginBodyContent));

		mockMvc.perform(post("/login")
			.content(serializedLoginBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(InvalidLoginParameterException.class));
	}

	@Test
	@Transactional
	public void login_로그인한_사람이_같은_아이디로_로그인요청() throws Exception {

		HashMap<String,String> signupBodyContent = new HashMap<>();
		signupBodyContent.put("id","jerry");
		signupBodyContent.put("password","1234");
		String serializedSignupBody = String.valueOf(new JSONObject(signupBodyContent));

		mockMvc.perform(post("/join")
			.content(serializedSignupBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		HashMap<String,String> loginBodyContent = new HashMap<>();
		loginBodyContent.put("id","jerry");
		loginBodyContent.put("password","1234");
		String serializedLoginBody = String.valueOf(new JSONObject(loginBodyContent));

		MvcResult mvcResult =mockMvc.perform(post("/login")
			.content(serializedLoginBody)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookie = mvcResult.getResponse().getCookie("SESSION");
		mockMvc.perform(post("/login")
			.content(serializedLoginBody)
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(DuplicateLoginException.class));
	}


	@Test
	@Transactional
	public void login_로그인한_사람이_다른_아이디로_로그인요청() throws Exception {

		HashMap<String,String> signupBodyContent = new HashMap<>();
		signupBodyContent.put("id","jerry");
		signupBodyContent.put("password","1234");
		String serializedBody1 = String.valueOf(new JSONObject(signupBodyContent));

		mockMvc.perform(post("/join")
			.content(serializedBody1)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		HashMap<String,String> signupBodyContent2 = new HashMap<>();
		signupBodyContent.put("id","adam");
		signupBodyContent.put("password","13523");
		String serializedBody2 = String.valueOf(new JSONObject(signupBodyContent));

		mockMvc.perform(post("/join")
			.content(serializedBody2)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());

		MvcResult mvcResult =mockMvc.perform(post("/login")
			.content(serializedBody1)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookie = mvcResult.getResponse().getCookie("SESSION");


		MvcResult mvcResult2 = mockMvc.perform(post("/login")
			.content(serializedBody2)
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		Cookie cookie2 = mvcResult2.getResponse().getCookie("SESSION");

		// 세션키 바뀜 검증
		assertThat(cookie).isNotEqualTo(cookie2);

		/* TODO: 기존 세션 만료 로직 테스트 */
		mockMvc.perform(get("/my-estate/test")
			.cookie(cookie)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AuthenticationException.class));
	}

	/* TODO: 로그인 상태에서 회원가입 요청 예외처리 테스트 */
}