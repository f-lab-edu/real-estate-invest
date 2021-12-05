package kancho.realestate.comparingprices.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ComparingGroup {
	private Long id;
	private Long userNo;
	private String name;
	private LocalDateTime deleteDttm;

	public ComparingGroup(Long userNo, String name) {
		this.userNo = userNo;
		this.name = name;
	}

	@Override
	public String toString() {
		return "ComparingGroup{" +
			"id=" + id +
			", userNo=" + userNo +
			", name='" + name + '\'' +
			", deleteDttm=" + deleteDttm +
			'}';
	}
}
