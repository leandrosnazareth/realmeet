package br.com.sw2you.realmeet.service;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.validator.RoomValidator;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomValidator roomValidator;


    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, RoomValidator roomValidator) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomValidator = roomValidator;
    }

    public RoomDTO getRoom(Long id) {
        //verificar se o objeto esta nulo
        Objects.requireNonNull(id);
        //roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException()); melhorar trocando a expressão lambda para método reference
        Room room = roomRepository.findByIdAndActive(id, true).orElseThrow(RoomNotFoundException::new);
//        converter room em roomDTO
        return roomMapper.fromEntityToDto(room);
    }

    public RoomDTO createRoom(CreateRoomDTO createRoomDTO){
        //validar os dados
        roomValidator.validate(createRoomDTO);
        //transformer roomDTO em rom
        var room = roomMapper.fromCreateRoomDtoToEntity(createRoomDTO);
        roomRepository.save(room);
        //transformar entity room em roomdto para retornar
        return roomMapper.fromEntityToDto(room);
    }
}
