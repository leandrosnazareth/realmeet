package br.com.sw2you.realmeet.validator;

public class ValidatorConstants {

    public static final String ROOM_NAME = ".name";
    public static final int ROOM_NAME_MAX_LENGTH = 20; // tamanho máximo definido no banco de dados
    public static final int ROOM_SEATS_MIN_VALUE = 1; // quantidade minima de assentos
    public static final int ROOM_SEATS_MAX_VALUE = 20; // quantidade maxima de assentos
    public static final String ROOM_SEATS = "seats";
    public static final String MISSING = ".missing";    //= ausente-> informar que está faltando informação
    public static final String EXCEEDS_MAX_LENGTH = ".exceedsMaxLength"; // tamanho máximo
    public static final String EXCEEDS_MAX_VALUE = ".exceedsMaxValue"; // maior que o valor mínimo
    public static final String BELOW_MIN_VALUE = ".belowMinValue"; // menor que o valor mínimo

    public ValidatorConstants() {
    }
}
