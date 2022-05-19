package br.com.sw2you.realmeet.validator;

public class ValidatorConstants {

    public static final String ROOM_ID = ".id";
    public static final String ROOM_NAME = ".name";
    public static final int ROOM_NAME_MAX_LENGTH = 20; // tamanho máximo definido no banco de dados
    public static final int ROOM_SEATS_MIN_VALUE = 1; // quantidade minima de assentos
    public static final int ROOM_SEATS_MAX_VALUE = 20; // quantidade maxima de assentos
    public static final String ROOM_SEATS = "seats";
    public static final String MISSING = ".missing";    //= ausente-> informar que está faltando informação
    public static final String EXCEEDS_MAX_LENGTH = ".exceedsMaxLength"; // tamanho máximo
    public static final String EXCEEDS_MAX_VALUE = ".exceedsMaxValue"; // maior que o valor mínimo
    public static final String BELOW_MIN_VALUE = ".belowMinValue"; // menor que o valor mínimo
    public static final String DUPLICATE = ".duplicate"; // duplicidade

    public static final String ALLOCATION_ID = "id";
    public static final String ALLOCATION_SUBJECT = "subject";
    public static final String ALLOCATION_EMPLOYEE_NAME = "employeeName";
    public static final String ALLOCATION_EMPLOYEE_EMAIL = "employeeEmail";
    public static final String ALLOCATION_START_AT = "startAt";
    public static final String ALLOCATION_END_AT = "endAt";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";
    public static final String EMAIL = "email";
    public static final String ORDER_BY = "orderBy";

    public static final int ALLOCATION_SUBJECT_MAX_LENGTH = 60;
    public static final int ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH = 20;
    public static final int ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH = 30;
    public static final int ALLOCATION_MAX_DURATION_SECONDS = 4 * 60 * 60; //duração maxima em segundos, permitida da reunião, total de 4 horas

    public static final String INCONSISTENT = ".inconsistent";
    public static final String IN_THE_PAST = ".inThePast";
    public static final String EXCEEDS_DURATION = ".exceedsDuration";//excedeu o tempo
    public static final String EXCEEDS_INTERVAL = ".exceedsInterval";
    public static final String OVERLAPS = ".overlaps";
    public static final String INVALID = ".invalid";

    public ValidatorConstants() {
    }
}
