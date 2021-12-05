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
import kancho.realestate.comparingprices.domain.dto.response.ResponseApartmentDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseUserDto;


@Transactional
@SpringBootTest
class ComparingGroupServiceTest {

	@Autowired
	private ComparingGroupService comparingGroupService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApartmentService apartmentService;

	private ResponseUserDto user;
	private ResponseApartmentDto apartment1;
	private ResponseApartmentDto apartment2;

	@BeforeEach
	void setUp() {
		// given
		RequestUserDto userDto = new RequestUserDto("testid", "12343");
		user = userService.createUser(userDto);

		RequestApartmentDto apartmentDto1 = new RequestApartmentDto("12345", "서울", "강남구",
			"역삼동", "23-23", "3423", "1230", "test name1", 1994,
			"test road2");
		RequestApartmentDto apartmentDto2 = new RequestApartmentDto("67894", "서울", "동작구",
			"본동", "5-3", "3-23", "130", "test name2", 1994,
			"test road2");
		apartment1 = apartmentService.save(apartmentDto1);
		apartment2 = apartmentService.save(apartmentDto2);
	}

	@DisplayName("비교그룹 등록")
	@Test
	void saveComparingGroup() {

		// when
		String groupName = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto = new RequestComparingGroupDto(user.getUserNo(),
			groupName);
		ResponseComparingGroupDto groupDto = comparingGroupService.saveComparingGroup(requestComparingGroupDto);

		// then
		assertThat(groupDto.getId()).isNotNull();
		assertThat(groupDto.getName()).isEqualTo(groupName);
	}

	@DisplayName("비교그룹에 아파트 등록 등록")
	@Test
	void saveComparingGroupItem() {
		// given
		String groupName = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto = new RequestComparingGroupDto(user.getUserNo(),
			groupName);
		ResponseComparingGroupDto groupDto =comparingGroupService.saveComparingGroup(requestComparingGroupDto);

		// when
		RequestGroupItemDto requestDto = new RequestGroupItemDto(groupDto.getId(), apartment1.getId());
		ResponseGroupItemDto responseGroupItemDto = comparingGroupService.saveGroupItem(requestDto);

		// then
		assertThat(responseGroupItemDto.getId()).isNotNull();
		assertThat(responseGroupItemDto.getGroupId()).isEqualTo(groupDto.getId());
	}

	@DisplayName("사용자가 등록한 비교그룹 조회")
	@Test
	void findComparingGroupsByUserNo() {
		// given
		String groupName1 = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto1 = new RequestComparingGroupDto(user.getUserNo(),
			groupName1);
		ResponseComparingGroupDto groupDto1 = comparingGroupService.saveComparingGroup(requestComparingGroupDto1);

		String groupName2 = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto2 = new RequestComparingGroupDto(user.getUserNo(),
			groupName2);
		ResponseComparingGroupDto groupDto2 = comparingGroupService.saveComparingGroup(requestComparingGroupDto2);

		// when
		List<ResponseComparingGroupDto> responses = comparingGroupService.findComparingGroupsByUserNoResponses(
			user.getUserNo());

		// then
		assertThat(responses.size()).isEqualTo(2);
		assertThat(responses.contains(groupDto1)).isTrue();
		assertThat(responses.contains(groupDto2)).isTrue();
	}


	@DisplayName("비교군 그룹 내 아파트 목록 조회")
	@Test
	void findGroupItemsByGroupIdResponses(){
		// given
		String groupName = "가장 관심가지는 곳";
		RequestComparingGroupDto requestComparingGroupDto = new RequestComparingGroupDto(user.getUserNo(),
			groupName);
		ResponseComparingGroupDto groupDto =comparingGroupService.saveComparingGroup(requestComparingGroupDto);

		RequestGroupItemDto groupItemDto1 = new RequestGroupItemDto(groupDto.getId(), apartment1.getId());
		RequestGroupItemDto groupItemDto2 = new RequestGroupItemDto(groupDto.getId(), apartment2.getId());
		ResponseGroupItemDto responseGroupItemDto1 = comparingGroupService.saveGroupItem(groupItemDto1);
		ResponseGroupItemDto responseGroupItemDto2 = comparingGroupService.saveGroupItem(groupItemDto2);

		// when
		List<ResponseGroupItemDto> responses = comparingGroupService.findGroupItemsByGroupIdResponses(
			groupDto.getId());

		// then
		assertThat(responses.contains(responseGroupItemDto1)).isTrue();
		assertThat(responses.contains(responseGroupItemDto2)).isTrue();
	}
}