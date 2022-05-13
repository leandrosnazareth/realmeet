package unit;

import static org.junit.Assert.assertEquals;
import static utils.MapperUtils.roomMapper;
import static utils.TestConstants.DEFAULT_ROOM_ID;
import static utils.TestDataCreator.newCreateRoomDTO;
import static utils.TestDataCreator.newRoomBuilder;

import br.com.sw2you.realmeet.mapper.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomMapperUnitTest {
    private RoomMapper victim;

    @BeforeEach
    void setupEach() {
        victim = roomMapper();
    }

    @Test
    void testFromEntityToDto() {
        var room = newRoomBuilder().id(DEFAULT_ROOM_ID).build();
        var dto = victim.fromEntityToDto(room);

        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
    }

    @Test
    void testCreateRoomDtoToEntity() {
        var createRoomDTO = newCreateRoomDTO();
        var room = victim.fromCreateRoomDtoToEntity(createRoomDTO);

        assertEquals(createRoomDTO.getName(), room.getName());
        assertEquals(createRoomDTO.getSeats(), room.getSeats());
    }
}