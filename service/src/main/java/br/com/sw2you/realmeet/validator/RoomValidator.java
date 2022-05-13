package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErros = new ValidationErrors();

        //ROOM VALIDATE
        //ValidatorUtils.validateRequired( static import
        validateRequired(createRoomDTO.getName(), ROOM_NAME, validationErros); // validar nome
        validateMaxLength(createRoomDTO.getName(), ROOM_NAME, ROOM_NAME_MAX_LENGTH, validationErros);//validar tamanho do nome maxlangth

        //SEATS VALIDATE
        validateRequired(createRoomDTO.getSeats(), ROOM_SEATS, validationErros); // validar nome
        validateMinValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validationErros); // validar quantidade mínima de assentos
        validateMaxValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validationErros); // validar quantidade máxima de assentos

        //verificar se existe erros na lista validationErros
        throwOnError(validationErros);

        //validar nome
        validateNameDuplicate(createRoomDTO.getName());
    }

    public void validateNameDuplicate(String name) {
        roomRepository.findByNameAndActive(name, true).ifPresent(__ -> {
            throw new InvalidRequestException(new ValidationError(ROOM_NAME, ROOM_NAME + DUPLICATE));
        });
    }
}
