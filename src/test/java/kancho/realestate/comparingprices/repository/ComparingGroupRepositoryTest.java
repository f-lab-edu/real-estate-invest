package kancho.realestate.comparingprices.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.User;

class ComparingGroupRepositoryTest extends DomainTest {

	@Autowired
	private ComparingGroupRepository comparingGroupRepository;

	@Autowired
	private UserRepository userRepository;

	@DisplayName("비교그룹 등록")
	@Test
	void saveComparingGroup() {
		// given
		String userId="testid12312";
		String pssword="password1234";
		User user = User.makeBasicAuthUser(userId, pssword);
		userRepository.save(user);

		// when
		String groupName="마래푸34 아리팍 24";
		ComparingGroup group = new ComparingGroup(user,groupName);
		comparingGroupRepository.save(group);

		// then
		List<ComparingGroup> findGroup = comparingGroupRepository.findComparingGroupByUser(user);
		assertThat(findGroup.size()).isEqualTo(1);
		assertThat(findGroup.get(0).getGroupName()).isEqualTo(groupName);
	}
}
