package hello.ebookstore.exception;

public class NoLoginMemberException extends RuntimeException{
    public NoLoginMemberException(String message) {
        super(message);
    }
}
