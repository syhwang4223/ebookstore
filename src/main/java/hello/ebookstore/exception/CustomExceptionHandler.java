package hello.ebookstore.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> badReqExHandle(BadRequestException e) {
        log.error("[exceptionHandler] ", e);
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> duplicationExHandle(DuplicateException e) {
        log.error("[exceptionHandler] ", e);
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ResponseMessage> loginFailExHandle(LoginFailException e) {
        log.error("[exceptionHandler] ", e);
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
    }


}
