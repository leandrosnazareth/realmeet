package br.com.sw2you.realmeet.validator;

import java.util.Objects;

public class ValidationError {
    private final String field;
    private final String errorCode;

    public ValidationError(String field, String errorCode) {
        this.field = field;
        this.errorCode = errorCode;
    }

    public String getField() {
        return field;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(field, that.field) && Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, errorCode);
    }

    @Override
    public String toString() {
        return "ValidationError{field='" + field + '\'' + ", errorCode='" + errorCode + '\'' + '}';
    }
}
