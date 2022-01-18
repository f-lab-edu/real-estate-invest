package kancho.realestate.comparingprices.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import kancho.realestate.comparingprices.acceptance.AcceptanceTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SoaringPricesControllerTest extends AcceptanceTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void 상승률이_높은_부동산_조회() throws Exception {
		mockMvc.perform(get("/soaring-prices/percent")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("급상승 부동산 가격 조회 성공")));
	}

	@Test
	void 상승금액이_높은_부동산_조회() throws Exception {
		mockMvc.perform(get("/soaring-prices/percent")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("급상승 부동산 가격 조회 성공")));
	}
}
