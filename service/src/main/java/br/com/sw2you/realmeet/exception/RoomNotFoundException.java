package br.com.sw2you.realmeet.exception;

//tratar as exception da class rom
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
