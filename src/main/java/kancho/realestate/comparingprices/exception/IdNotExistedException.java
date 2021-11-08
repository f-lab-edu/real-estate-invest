package kancho.realestate.comparingprices.exception;

public class IdNotExistedException extends RuntimeException{

    public IdNotExistedException(){
        super("존재하지 않는 ID 입니다.");
    }
}
