package kancho.realestate.comparingprices.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ComparingGroupTest {


	@DisplayName("비교그룹 생성")
	@Test
	void comparingGroup(){
		String groupName="마래푸34 아리팍24";
		ComparingGroup group = new ComparingGroup(new User("abc","1234"),groupName);
		Assertions.assertThat(group.getGroupName()).isEqualTo(groupName);
	}
}
