package br.com.sw2you.realmeet.exception;

import br.com.sw2you.realmeet.validator.ValidationErrors;

public class InvalidRequestException extends RuntimeException {

    private final ValidationErrors validationErrors;

    public InvalidRequestException(ValidationErrors validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = validationErrors;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
