package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //validar create
    public void validate(CreateRoomDTO createRoomDTO) {
        var validationErros = new ValidationErrors();

        //se um teste passar faz o outro
        if (
            //validar room
            validateRomName(createRoomDTO.getName(), validationErros) &&
            // validar seats
            validateRoomSeats(createRoomDTO.getSeats(), validationErros)
        ) {
            //validar nome
            validateNameDuplicate(null, createRoomDTO.getName(), validationErros);
        }

        //verificar se existe erros na lista validationErros
        throwOnError(validationErros);
    }

    //validar update
    public void validate(Long roomId, UpdateRoomDTO updateRoomDTO) {
        var validationErros = new ValidationErrors();

        //se um teste passar faz o outro
        if (
            validateRequired(roomId, ROOM_ID, validationErros) &&
            //validar room
            validateRomName(updateRoomDTO.getName(), validationErros) &&
            // validar seats
            validateRoomSeats(updateRoomDTO.getSeats(), validationErros)
        ) {
            //validar nome
            validateNameDuplicate(roomId, updateRoomDTO.getName(), validationErros);
        }

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

    private void validateNameDuplicate(Long roomIdToExclude, String name, ValidationErrors validationErrors) {
        roomRepository
                .findByNameAndActive(name, true)
                .ifPresent(
                        room -> {
                            if (isNull(roomIdToExclude) || !Objects.equals(room.getId(), roomIdToExclude)) {
                                validationErrors.add(ROOM_NAME, ROOM_NAME + DUPLICATE);
                            }
                        }
                );
    }
}
