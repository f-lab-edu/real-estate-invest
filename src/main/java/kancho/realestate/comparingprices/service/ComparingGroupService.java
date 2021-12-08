package kancho.realestate.comparingprices.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseGroupItemDto;
import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import kancho.realestate.comparingprices.repository.ComparingGroupMapper;
import kancho.realestate.comparingprices.repository.GroupItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComparingGroupService {

	private final ComparingGroupMapper comparingGroupMapper;
	private final GroupItemMapper groupItemMapper;

	@Transactional
	public ResponseComparingGroupDto saveComparingGroup(RequestComparingGroupDto requestDto){
		ComparingGroup comparingGroup = requestDto.toComparingGroup();
		comparingGroupMapper.saveComparingGroup(comparingGroup);
		return ResponseComparingGroupDto.from(comparingGroup);
	}

	public List<ResponseComparingGroupDto> findComparingGroupsByUserNoResponses(Long userNo){
		return  findComparingGroupsByUserNo(userNo).stream()
			.map(ResponseComparingGroupDto::from)
			.collect(Collectors.toList());
	}

	public List<ComparingGroup> findComparingGroupsByUserNo(Long userNo){
		return comparingGroupMapper.findComparingGroupsByUserNo(userNo);
	}

	@Transactional
	public ResponseGroupItemDto saveGroupItem(RequestGroupItemDto requestDto){
		GroupItem groupItem = requestDto.toGroupItem();
		groupItemMapper.saveGroupItem(groupItem);
		return ResponseGroupItemDto.from(groupItem);
	}

	public List<GroupItem> findGroupItemsByGroupId(Long groupId){
		return groupItemMapper.findByGroupId(groupId);
	}

	public List<ResponseGroupItemDto> findGroupItemsByGroupIdResponses(Long groupId){
		return findGroupItemsByGroupId(groupId)
			.stream()
			.map(ResponseGroupItemDto::from)
			.collect(Collectors.toList());
	}
}
