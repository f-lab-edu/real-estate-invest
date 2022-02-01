package kancho.realestate.comparingprices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.SuccessReponseDto;
import kancho.realestate.comparingprices.service.ComparingGroupService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comparing-group", produces = "application/json; charset=utf8")
public class ComparingGroupController {

	private final ComparingGroupService comparingGroupService;

	@PostMapping
	public ResponseEntity createComparingGroup(@RequestBody RequestComparingGroupDto requestComparingGroupDto) {
		ResponseComparingGroupDto savedComparingGroup = comparingGroupService.saveComparingGroup(requestComparingGroupDto);
		return new ResponseEntity<>(new SuccessReponseDto<>("그룹이 등록되었습니다.", savedComparingGroup), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity showComparingGroupsInUser(@RequestParam("userId") Long userId) {
		List<ResponseComparingGroupDto> groupResponses = comparingGroupService
			.findComparingGroupsByUserIdResponses(userId);
		return new ResponseEntity<>(new SuccessReponseDto<>("그룹 목록 조회", groupResponses), HttpStatus.OK);
	}

	@PostMapping("/{groupId}/groupItems")
	public ResponseEntity createComparingGroupItem(@PathVariable Long groupId,
		@RequestBody RequestGroupItemDto requestGroupItemDto) {
		// TODO: JPA로 전환시 groupId로 ComparingGroup 객체를 조회. RequestGroupItemDto에서는 groupId 제외
		ResponseGroupItemDto savedGroupItem = comparingGroupService.saveGroupItem(groupId, requestGroupItemDto);
		return new ResponseEntity<>(new SuccessReponseDto<>("그룹에 추가 되었습니다.", savedGroupItem), HttpStatus.CREATED);
	}

	@GetMapping("/{groupId}/groupItems")
	public ResponseEntity showItemsInComparingGroup(@RequestParam("groupId") Long groupId) {
		List<ResponseGroupItemDto> groupItemsResponses = comparingGroupService.findGroupItemsByGroupIdResponses(
			groupId);
		return new ResponseEntity<>(new SuccessReponseDto<>("그룹에 있는 아파트 목록 조회", groupItemsResponses), HttpStatus.OK);
	}
}
