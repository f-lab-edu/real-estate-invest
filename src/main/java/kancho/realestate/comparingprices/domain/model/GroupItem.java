package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;


@Getter
@Entity
public class GroupItem extends BaseTimeEntity{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private ComparingGroup comparingGroup;

	@OneToOne(fetch = FetchType.LAZY)
	private Apartment apartment;

	private LocalDateTime deleteTime;

	protected GroupItem() {
	}

	public GroupItem(ComparingGroup comparingGroup, Apartment apartment) {
		this.comparingGroup = comparingGroup;
		this.apartment = apartment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof GroupItem))
			return false;
		GroupItem groupItem = (GroupItem)o;
		return Objects.equals(getId(), groupItem.getId()) && Objects.equals(getComparingGroup(),
			groupItem.getComparingGroup());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getComparingGroup());
	}

	@Override
	public String toString() {
		return "GroupItem{" +
			"id=" + id +
			", comparingGroup=" + comparingGroup +
			", apartment=" + apartment +
			", deleteTime=" + deleteTime +
			'}';
	}
}
