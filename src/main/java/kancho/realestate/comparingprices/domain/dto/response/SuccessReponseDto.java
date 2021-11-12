package kancho.realestate.comparingprices.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessReponseDto<T> {
    private String message;
    private T result;
}
