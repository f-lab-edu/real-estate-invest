package kancho.realestate.comparingprices.domain.dto.response;

import lombok.Getter;

@Getter
public class SuccessReponseDto<T> {
    private String message;
    private T result;

    public SuccessReponseDto() {
    }

    public SuccessReponseDto(String message, T result) {
        this.message = message;
        this.result = result;
    }
}
