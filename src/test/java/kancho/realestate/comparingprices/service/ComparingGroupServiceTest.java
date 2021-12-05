package kancho.realestate.comparingprices.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestApartmentDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestUserDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import kancho.realestate.comparingprices.domain.model.User;

@Transactional
@SpringBootTest
class ComparingGroupServiceTest {

	@Autowired
	private ComparingGroupService comparingGroupService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApartmentService apartmentService;

	private User user;
	private Apartment apartment1;
	private Apartment apartment2;

	@BeforeEach
	void setUp() {
		// given
		RequestUserDto userDto = new RequestUserDto("testid", "12343");
		userService.createUser(userDto);
		user = userService.getUserById(userDto.getId()).orElseThrow(IllegalStateException::new);
		RequestApartmentDto apartmentDto1 = new RequestApartmentDto("12345", "서울", "강남구",
			"역삼동", "23-23", "3423", "1230", "test name1", 1994,
			"test road2");
		RequestApartmentDto apartmentDto2 = new RequestApartmentDto("67894", "서울", "동작구",
			"본동", "5-3", "3-23", "130", "test name2", 1994,
			"test road2");
		apartmentService.save(apartmentDto1);
		apartmentService.save(apartmentDto2);
		apartment1 = apartmentService.findApartmentByDistinguishable(apartmentDto1);
		apartment2 = apartmentService.findApartmentByDistinguishable(apartmentDto2);
	}

	@DisplayName("비교그룹 등록")
	@Test
	void saveComparingGroup() {

		// when
		String groupName = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto = new RequestComparingGroupDto(user.getUserNo(), groupName
		);
		comparingGroupService.saveComparingGroup(requestComparingGroupDto);

		// then
		List<ComparingGroup> comparingGroups = comparingGroupService.findComparingGroupsByUserNo(user.getUserNo());

		assertThat(comparingGroups.size()).isEqualTo(1);
		assertThat(comparingGroups.get(0).getName()).isEqualTo(groupName);
	}

	@DisplayName("비교그룹에 아파트 등록 등록")
	@Test
	void saveComparingGroupItem() {
		// given
		String groupName = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto = new RequestComparingGroupDto(user.getUserNo(), groupName
		);
		comparingGroupService.saveComparingGroup(requestComparingGroupDto);
		ComparingGroup comparingGroup = comparingGroupService.findComparingGroupsByUserNo(user.getUserNo()).get(0);

		// when
		RequestGroupItemDto groupItemDto1 = new RequestGroupItemDto(comparingGroup.getId(),apartment1.getId());
		RequestGroupItemDto groupItemDto2 = new RequestGroupItemDto(comparingGroup.getId(),apartment2.getId());
		comparingGroupService.saveGroupItem(groupItemDto1);
		comparingGroupService.saveGroupItem(groupItemDto2);

		// then
		List<GroupItem> groupItems = comparingGroupService.findGroupItemsByGroupId(comparingGroup.getId());

		assertThat(groupItems.size()).isEqualTo(2);
	}

}