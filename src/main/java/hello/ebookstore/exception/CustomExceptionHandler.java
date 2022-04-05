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
    public ResponseEntity<ErrorResult> invalidReqExHandle(InvalidRequestException e) {
        log.error("[exceptionHandler] ", e);
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> duplicationExHandle(DuplicateException e) {
        log.error("[exceptionHandler] ", e);
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResult> loginFailExHandle(LoginFailException e) {
        log.error("[exceptionHandler] ", e);
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> noAuthenticationExHandle(NoAuthenticationException e) {
        log.error("[exceptionHandler] ", e);
        ErrorResult errorResult = new ErrorResult(e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
    }


}
