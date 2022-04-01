package hello.ebookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NoSuchCategoryException extends RuntimeException {

    public NoSuchCategoryException(String message) {
        super(message);
    }

}
