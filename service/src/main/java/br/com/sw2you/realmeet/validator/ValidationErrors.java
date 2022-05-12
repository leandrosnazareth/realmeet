package br.com.sw2you.realmeet.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.util.Streamable;

public class ValidationErrors implements Streamable<ValidationError> {

    private List<ValidationError> validationErrorList;


    public ValidationErrors() {
        this.validationErrorList = new ArrayList<>();
    }

    public ValidationErrors add(String field, String errorCode) {
        return add(new ValidationError(field, errorCode));
    }

    public ValidationErrors add(ValidationError validationError) {
        validationErrorList.add(validationError);
        return this;
    }

    //retornar o erro com base no index
    public ValidationError getError(int index) {
        return validationErrorList.get(index);
    }

    //retornar quantidade de erros
    public int numberOfErros() {
        return validationErrorList.size();
    }

    //verificar se tem erros na lista
    public boolean hasErros() {
        //verifica se a lista não tem itens
        return !validationErrorList.isEmpty();
    }

    @Override
    public String toString() {
        return "ValidationErrors{" +
                "validationErrorList=" + validationErrorList +
                '}';
    }

    @Override
    public Iterator<ValidationError> iterator() {
        return validationErrorList.iterator();
    }
}
