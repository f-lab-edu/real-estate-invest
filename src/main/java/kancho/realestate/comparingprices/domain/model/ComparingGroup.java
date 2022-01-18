package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Getter
@Entity
public class ComparingGroup extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private String groupName;

	private LocalDateTime deleteDttm;

	protected ComparingGroup() {
	}

	public ComparingGroup(User user, String groupName) {
		this.user = user;
		this.groupName = groupName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ComparingGroup))
			return false;
		ComparingGroup that = (ComparingGroup)o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "ComparingGroup{" +
			"id=" + id +
			", groupName='" + groupName + '\'' +
			", deleteDttm=" + deleteDttm +
			'}';
	}
}
