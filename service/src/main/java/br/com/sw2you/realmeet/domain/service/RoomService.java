package br.com.sw2you.realmeet.domain.service;

import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;


    //usar construtor ao inves do @autowired
    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomDTO getRoom(Long id) {
        //verificar se o objeto está nulo
        Objects.requireNonNull(id);
        //roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException()); melhorar trocando a exprecao lambda para metodo reference
        Room room = roomRepository.findByIdAndActive(id, true).orElseThrow(RoomNotFoundException::new);
//        converter room em roomDTO
        return roomMapper.fromEntityToDto(room);
    }
}
