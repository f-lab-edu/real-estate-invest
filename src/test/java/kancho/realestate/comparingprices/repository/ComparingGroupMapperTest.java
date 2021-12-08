package kancho.realestate.comparingprices.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class ComparingGroupMapperTest {

	@Autowired
	private ComparingGroupMapper comparingGroupMapper;

	@Autowired
	private UserMapper userMapper;

	@DisplayName("비교그룹 등록")
	@Test
	void saveComparingGroup() {
		// given
		String userId="testid12312";
		String pssword="password1234";
		User user = new User(userId, pssword);
		userMapper.saveUser(user);

		// when
		String groupName="마래푸34 아리팍 24";
		ComparingGroup group = new ComparingGroup(user.getUserNo(),groupName);
		comparingGroupMapper.saveComparingGroup(group);

		// then
		List<ComparingGroup> findGroup = comparingGroupMapper.findComparingGroupsByUserNo(user.getUserNo());
		assertThat(findGroup.size()).isEqualTo(1);
		assertThat(findGroup.get(0).getName()).isEqualTo(groupName);
	}
}