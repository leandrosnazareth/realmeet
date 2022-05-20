package br.com.sw2you.realmeet.exception;

//tratar as exception da class allocation
public class AllocationNotFoundException extends RuntimeException {

    public AllocationNotFoundException(String message) {
        super(message);
    }
}
