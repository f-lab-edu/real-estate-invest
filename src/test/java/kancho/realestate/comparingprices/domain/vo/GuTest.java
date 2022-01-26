package kancho.realestate.comparingprices.domain.vo;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kancho.realestate.comparingprices.domain.vo.Gu;

class GuTest {

	@Test
	void 존재하는_지역번호를_찾으면_true_반환() {
		String existingRegionalCode = "11680";

		boolean isCorrect = Gu.isCorrectRegionalCode(existingRegionalCode);

		assertThat(isCorrect).isTrue();
	}

	@Test
	void 존재하지_않는_지역번호를_찾으면_false_반환() {
		String existingRegionalCode = "4444";

		boolean isCorrect = Gu.isCorrectRegionalCode(existingRegionalCode);

		assertThat(isCorrect).isFalse();
	}
}
