package kancho.realestate.comparingprices.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
}