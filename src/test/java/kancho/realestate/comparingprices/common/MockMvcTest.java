package kancho.realestate.comparingprices.common;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * controller, 인수테스트 공통 설정 클래스
 */

@Transactional
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MockMvcTest {

	@Autowired
	private DatabaseCleanup databaseCleanup;

	@BeforeEach
	public void init() {
		databaseCleanup.execute();
	}
}
