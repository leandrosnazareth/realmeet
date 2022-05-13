package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErros = new ValidationErrors();
        //validar room
        validateRomName(createRoomDTO.getName(), validationErros);
        // validar seats
        validateRoomSeats(createRoomDTO.getSeats(), validationErros);
        //validar nome
        validateNameDuplicate(createRoomDTO.getName(), validationErros);
        //verificar se existe erros na lista validationErros
        throwOnError(validationErros);
    }

    private boolean validateRomName(String name, ValidationErrors validationErros) {
        //ROOM VALIDATE
        //ValidatorUtils.validateRequired( static import
        return (
                validateRequired(name, ROOM_NAME, validationErros) && // validar nome
                        validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErros));//validar tamanho do nome maxlangth
    }

    private boolean validateRoomSeats(Integer seats, ValidationErrors validationErros) {
        //SEATS VALIDATE
        return (
                validateRequired(seats, ROOM_SEATS, validationErros) && // validar nome
                        validateMinValue(seats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErros) && // validar quantidade mínima de assentos
                        validateMaxValue(seats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErros) // validar quantidade máxima de assentos
        );
    }

    public void validateNameDuplicate(String name, ValidationErrors validationErros) {
        roomRepository.findByNameAndActive(name, true)
                .ifPresent(__ -> validationErros.add(ROOM_NAME, ROOM_NAME + DUPLICATE));//add erro na lista de erros
    }
}
