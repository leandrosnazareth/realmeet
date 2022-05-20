package br.com.sw2you.realmeet.config;


import static br.com.sw2you.realmeet.util.ResponseEntityUtils.notFound;

import br.com.sw2you.realmeet.api.model.ResponseError;
import br.com.sw2you.realmeet.exception.AllocationNotFoundException;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ RoomNotFoundException.class, AllocationNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception) {
        return notFound();
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ResponseError> handleInvalidRequestException(InvalidRequestException exception) {
        return exception
                .getValidationErrors()
                .stream()
                .map(e -> new ResponseError().field(e.getField()).errorCode(e.getErrorCode()))
                .collect(Collectors.toList());
    }
}
