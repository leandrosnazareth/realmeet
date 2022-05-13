package unit;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.MapperUtils.roomMapper;
import static utils.TestConstants.DEFAULT_ROOM_ID;
import static utils.TestDataCreator.newCreateRoomDTO;
import static utils.TestDataCreator.newRoomBuilder;

import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.validator.RoomValidator;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomServiceUnitTest {
    private RoomService victim; //vítima

    @Mock // tests com inject de dependência de objetos fictícios para independer do banco de dados
    private RoomRepository roomRepository;
    @Mock // tests com inject de dependência de objetos fictícios para independer do banco de dados
    private RoomValidator roomValidator;

    @BeforeEach
    void setupEach() {
        victim = new RoomService(roomRepository, roomMapper(), roomValidator);
    }

    @Test
    void testGetRomSuccess() {
        var room = newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        when(roomRepository.findByIdAndActive(DEFAULT_ROOM_ID, true)).thenReturn(Optional.of(room));

        var dto = victim.getRoom(DEFAULT_ROOM_ID);
        Assertions.assertEquals(room.getId(), dto.getId());
        Assertions.assertEquals(room.getName(), dto.getName());
        Assertions.assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testGetRomNotFound() {
        when(roomRepository.findByIdAndActive(DEFAULT_ROOM_ID, true)).thenReturn(Optional.empty());//Optional empty verificar se foi encontrado
        assertThrows(RoomNotFoundException.class, () -> victim.getRoom(DEFAULT_ROOM_ID));
    }

    @Test
    void testCreateRoomSuccess() {
        var createRoomDTO = newCreateRoomDTO();
        var roomDTO = victim.createRoom(createRoomDTO);

        Assertions.assertEquals(createRoomDTO.getName(), roomDTO.getName());
        Assertions.assertEquals(createRoomDTO.getSeats(), roomDTO.getSeats());
        //verificar se o método save foi chamado
        verify(roomRepository).save(any());
    }
}