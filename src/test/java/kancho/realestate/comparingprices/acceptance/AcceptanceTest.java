package kancho.realestate.comparingprices.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * controller, 인수테스트 공통 설정 클래스
 */

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AcceptanceTest {

	@Autowired
	private DatabaseCleanup databaseCleanup;

	@BeforeEach
	public void init() {
		databaseCleanup.execute();
	}
}
