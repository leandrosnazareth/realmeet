package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

import br.com.sw2you.realmeet.exception.InvalidRequestException;

public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    //recebe a lista de erros e verifica se existe erros
    public static void throwOnError(ValidationErrors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new InvalidRequestException(validationErrors);
        }
    }

    public static boolean validateRequired(String field, String fieldName, ValidationErrors validationErrors) {
        //verificar se o field está em branco vazio ou nulo
        if (isBlank(field)) {
            //se sim, add a lista de erros
            validationErrors.add(fieldName, fieldName + MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateRequired(Object field, String fieldName, ValidationErrors validationErrors) {
        //verificar se o object é nulo
        if (isNull(field)) {
            //se sim, add a lista de erros
            validationErrors.add(fieldName, fieldName + MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateMaxLength(String field, String fieldName, int maxLength, ValidationErrors validationErrors) {
        //verificar se o field mão está em branco vazio ou nulo e
        if (!isBlank(field) && field.trim().length() > maxLength) {
            //se sim, add a lista de erros
            validationErrors.add(fieldName, fieldName + EXCEEDS_MAX_LENGTH);
            return false;
        }
        return true;
    }

    public static boolean validateMaxValue(Integer field, String fieldName, int maxValue, ValidationErrors validationErrors) {
        //verificar se o field mão está é nulo e se o tamanho não é maior que o máximo permitido
        if (!isNull(field) && field > maxValue) {
            //se sim, add a lista de erros
            validationErrors.add(fieldName, fieldName + EXCEEDS_MAX_VALUE);
            return false;
        }
        return true;
    }

    public static boolean validateMinValue(Integer field, String fieldName, int minValue, ValidationErrors validationErrors) {
        //verificar se o field mão está é nulo e se o tamanho não é menor que o mínimo permitido
        if (!isNull(field) && field < minValue) {
            //se sim, add a lista de erros
            validationErrors.add(fieldName, fieldName + BELOW_MIN_VALUE);
            return false;
        }
        return true;
    }
}