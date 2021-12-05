package kancho.realestate.comparingprices.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import kancho.realestate.comparingprices.repository.ComparingGroupMapper;
import kancho.realestate.comparingprices.repository.GroupItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComparingGroupService {

	private final ComparingGroupMapper comparingGroupMapper;
	private final GroupItemMapper groupItemMapper;

	public void saveComparingGroup(RequestComparingGroupDto requestDto){
		comparingGroupMapper.saveComparingGroup(requestDto.toComparingGroup());
	}

	public List<ResponseComparingGroupDto> findComparingGroupsByUserNoResponses(Long userNo){
		return  findComparingGroupsByUserNo(userNo).stream()
			.map(ResponseComparingGroupDto::from)
			.collect(Collectors.toList());
	}

	public List<ComparingGroup> findComparingGroupsByUserNo(Long userNo){
		return comparingGroupMapper.findComparingGroupsByUserNo(userNo);
	}

	public void saveGroupItem(RequestGroupItemDto requestDto){
		groupItemMapper.saveGroupItem(requestDto.toGroupItem());
	}

	public List<GroupItem> findByGroupId(Long groupId){
		return groupItemMapper.findByGroupId(groupId);
	}
}
