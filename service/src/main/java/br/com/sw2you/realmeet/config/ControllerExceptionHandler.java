package br.com.sw2you.realmeet.config;

import br.com.sw2you.realmeet.api.model.ResponseError;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception exception) {
        return buildresponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<Object> buildresponseEntity(HttpStatus httpStatus, Exception exception) {
        return new ResponseEntity<>(
                new ResponseError()
                        .status(httpStatus.getReasonPhrase())
                        .code(httpStatus.value())
                        .message(exception.getMessage()),
                httpStatus
        );
    }
}
