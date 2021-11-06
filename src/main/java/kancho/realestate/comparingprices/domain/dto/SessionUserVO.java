package kancho.realestate.comparingprices.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUserVO implements Serializable {
	private Long userNo;
	private String id;
}