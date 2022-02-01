package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kancho.realestate.comparingprices.repository.DomainTest;

class UserTest extends DomainTest {

	@Autowired
	private EntityManager em;

	@Test
	void 마지막_로그인_시각_변경() {
		//given
		User user = User.makeBasicAuthUser("tester1","1235df123");
		em.persist(user);

		//when
		LocalDateTime loginTime = LocalDateTime.now();
		user.updateLastLoginDttm(loginTime);
		em.flush();
		em.clear();

		//then
		User findUser = em.find(User.class, user.getId());
		Assertions.assertThat(findUser.getLastLoginDttm()).isEqualTo(loginTime);
	}
}
