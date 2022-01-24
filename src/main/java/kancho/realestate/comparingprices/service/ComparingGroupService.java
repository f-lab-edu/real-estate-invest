package kancho.realestate.comparingprices.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kancho.realestate.comparingprices.domain.dto.request.RequestComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.request.RequestGroupItemDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseComparingGroupDto;
import kancho.realestate.comparingprices.domain.dto.response.ResponseGroupItemDto;
import kancho.realestate.comparingprices.domain.model.Apartment;
import kancho.realestate.comparingprices.domain.model.ComparingGroup;
import kancho.realestate.comparingprices.domain.model.GroupItem;
import kancho.realestate.comparingprices.domain.model.User;
import kancho.realestate.comparingprices.repository.ApartmentRepository;
import kancho.realestate.comparingprices.repository.ComparingGroupRepository;
import kancho.realestate.comparingprices.repository.GroupItemRepository;
import kancho.realestate.comparingprices.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComparingGroupService {

	private final ApartmentRepository apartmentRepository;
	private final ComparingGroupRepository comparingGroupRepository;
	private final GroupItemRepository groupItemRepository;
	private final UserRepository userRepository;

	@Transactional
	public ResponseComparingGroupDto saveComparingGroup(RequestComparingGroupDto requestDto){
		User user = userRepository.findById(requestDto.getUserId()).orElseThrow(EntityNotFoundException::new);
		ComparingGroup comparingGroup = requestDto.toComparingGroup(user);
		comparingGroupRepository.save(comparingGroup);
		return ResponseComparingGroupDto.from(comparingGroup);
	}

	public List<ResponseComparingGroupDto> findComparingGroupsByUserIdResponses(Long userId){
		User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
		return  findComparingGroupsByUser(user).stream()
			.map(ResponseComparingGroupDto::from)
			.collect(Collectors.toList());
	}

	public List<ComparingGroup> findComparingGroupsByUser(User user){
		return comparingGroupRepository.findComparingGroupByUser(user);
	}

	@Transactional
	public ResponseGroupItemDto saveGroupItem(Long groupId, RequestGroupItemDto requestDto){
		Apartment apartment = apartmentRepository.findById(requestDto.getApartmentId())
			.orElseThrow(EntityNotFoundException::new);
		ComparingGroup comparingGroup = comparingGroupRepository.findById(groupId)
			.orElseThrow(EntityNotFoundException::new);
		GroupItem groupItem = requestDto.toGroupItem(comparingGroup,apartment);
		groupItemRepository.save(groupItem);
		return ResponseGroupItemDto.from(groupItem);
	}

	public List<GroupItem> findGroupItemsByGroupId(ComparingGroup comparingGroup){
		return groupItemRepository.findGroupItemByComparingGroup(comparingGroup);
	}

	public List<ResponseGroupItemDto> findGroupItemsByGroupIdResponses(Long groupId){
		ComparingGroup comparingGroup = comparingGroupRepository.findById(groupId)
			.orElseThrow(EntityNotFoundException::new);
		return findGroupItemsByGroupId(comparingGroup)
			.stream()
			.map(ResponseGroupItemDto::from)
			.collect(Collectors.toList());
	}
}
