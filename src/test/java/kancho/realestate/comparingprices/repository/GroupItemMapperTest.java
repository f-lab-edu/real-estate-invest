package kancho.realestate.comparingprices.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import kancho.realestate.comparingprices.domain.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class GroupItemMapperTest {

	@Autowired
	GroupItemMapper groupItemMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ComparingGroupMapper comparingGroupMapper;

	@Autowired
	private ApartmentMapper apartmentMapper;

	private ComparingGroup comparingGroup;

	@BeforeEach
	void setUp(){

		// given
		String userId="testid12312";
		String pssword="password1234";
		User user = new User(userId, pssword);
		userMapper.insertUser(user);
		User findUser = userMapper.selectUserById(userId).orElseThrow(IllegalStateException::new);

		String groupName="마래푸34 아리팍 24";
		ComparingGroup group = new ComparingGroup(findUser.getUserNo(),groupName);
		comparingGroupMapper.saveComparingGroup(group);

		List<ComparingGroup> findGroups = comparingGroupMapper.findComparingGroupsByUserNo(findUser.getUserNo());
		comparingGroup=findGroups.get(0);
	}

	@DisplayName("비교그룹에 아파트 등록")
	@Transactional
	@Test
	void saveGroupItem(){

		// given
		Apartment apartment = new Apartment( "12345", "서울", "강남구", "역삼동",
			"23-23", "3423", "1230", "test name", 1994,
			"test road");
		apartmentMapper.save(apartment);
		Apartment findApartment = apartmentMapper.findByRegionalCodeAndDongAndJibunAndApartmentName(apartment)
			.orElseThrow(IllegalStateException::new);
		// when
		GroupItem item = new GroupItem(comparingGroup.getId(),findApartment.getId());
		groupItemMapper.saveGroupItem(item);

		// then
		List<GroupItem> groupItems = groupItemMapper.findByGroupId(comparingGroup.getId());
		assertThat(groupItems.size()).isEqualTo(1);
		assertThat(groupItems.get(0).getApartmentId()).isEqualTo(findApartment.getId());

		//TODO: 1)중복등록 방지 기능, 2)삭제했다가 다시 등록할경우, update 처리, 3)삭제 기능
	}
}