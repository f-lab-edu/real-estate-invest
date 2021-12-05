package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class GroupItem {

	private Long id;
	private Long groupId;
	private Long apartmentId;
	private LocalDateTime deleteTime;

	public GroupItem(Long groupId, Long apartmentId) {
		this.groupId = groupId;
		this.apartmentId = apartmentId;
	}

	@Override
	public String toString() {
		return "GroupItem{" +
			"id=" + id +
			", groupId=" + groupId +
			", apartmentId=" + apartmentId +
			", deleteTime=" + deleteTime +
			'}';
	}
}
