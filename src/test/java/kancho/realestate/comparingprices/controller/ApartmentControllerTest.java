package kancho.realestate.comparingprices.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class ApartmentControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ApartmentController apartmentController;

	@Test
	public void 아파트_목록_조회() throws Exception {
		mockMvc.perform(get("/apartments")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}