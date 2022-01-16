package kancho.realestate.comparingprices.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ExceptionResponse {

    private String message;

    private ExceptionResponse() {
    }

    public ExceptionResponse(String message) {
        this.message = message;
    }
}
