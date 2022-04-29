package br.com.sw2you.realmeet.domain.service;

import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    //usar construtor ao inves do @autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findById(Long id) {
        //verificar se o objeto está nulo
        Objects.requireNonNull(id);
        //roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException()); melhorar trocando a expreção lambda para metodo reference
        return roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);
    }
}
